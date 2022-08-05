package htw.berlin.clientserver.clientserver.web.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.berlin.client.api.chart.DataSummary;
import htw.berlin.client.api.chart.ModuleGrade;
import htw.berlin.client.api.chart.Transcript;
import htw.berlin.clientserver.clientserver.dto.*;
import htw.berlin.clientserver.clientserver.dto.viewModel.Chart;
import htw.berlin.clientserver.clientserver.dto.viewModel.GradesChart;
import htw.berlin.clientserver.clientserver.web.chart.ChartController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.oauth2.client.ClientAuthorizationRequiredException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.swing.table.TableRowSorter;
import javax.websocket.server.PathParam;
import javax.xml.crypto.Data;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
public class DataController {

    private static final Logger LOG = LoggerFactory.getLogger(DataController.class);
    private static final  String CREATE_CHART_SERVICE_URL = "http://localhost:8081/chart-composite/createData";
    private static final  String CHART_SERVICE_URL = "http://localhost:8081/chart-composite";
    private static final  String CHART_DATA_SERVICE_URL = "http://localhost:8081/chart-composite/chart-data";


    private final WebClient webClient;

    private final ObjectMapper mapper;

    @Autowired
    public DataController(WebClient webClient, ObjectMapper mapper) {
        this.mapper = mapper;
        this.webClient = webClient;
    }

    @GetMapping(value = "/addData")
    public String addNewData(@RequestParam String chartId, Model model){
        DataDto dataDto = new DataDto();

        model.addAttribute("chartId", chartId);
        model.addAttribute("data", dataDto);
        return "newData";
    }

    @PostMapping(value = "/addData")
    public String postNewData(@RequestParam String chartId,
                              @ModelAttribute DataDto dataDto,
                              @RegisteredOAuth2AuthorizedClient("chart-client-authorization-code")
                              OAuth2AuthorizedClient authorizedClient,
                              Model model){

        dataDto.setChartId(chartId);
        String dataId = UUID.randomUUID().toString();
        dataDto.setDataId(dataId);
        dataDto.setStudentId(authorizedClient.getPrincipalName());

        Transcript transcript = new Transcript(dataDto.getSemester(), dataDto.getSemesterYear(), dataDto.getGrades(), 0, 0, 0.0);
        DataSummary dataSummary = new DataSummary(dataDto.getDataId(), dataDto.getChartId(), dataDto.getStudentId(), transcript);

        try {
            this.webClient.post()
                    .uri(CREATE_CHART_SERVICE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(dataSummary), DataSummary.class)
                    .attributes(oauth2AuthorizedClient(authorizedClient))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }catch (ClientAuthorizationRequiredException e){
            LOG.warn(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return "redirect:/home";
    }

    @GetMapping(value = "editeChart")
    public String getEditeChart(@RequestParam String chartId,
                                @ModelAttribute GradesChart chartModel,
                                @RegisteredOAuth2AuthorizedClient("chart-client-authorization-code")
                                OAuth2AuthorizedClient authorizedClient,
                                Model model){

        URI uri = UriComponentsBuilder.fromUriString(CHART_DATA_SERVICE_URL + "/{chartId}").build(chartId);

        try {
            ChartAggregate charts = this.webClient
                    .get()
                    .uri(uri)
                    .attributes(oauth2AuthorizedClient(authorizedClient))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(ChartAggregate.class)
                    .block();

            if (charts != null){
                List<DataDto> data = charts.getDataSummaryList().stream().map( dataSummary ->
                        new DataDto(dataSummary.getDataId(), dataSummary.getChartId(), dataSummary.getStudentId(),
                                dataSummary.getTranscript().getSemester(),
                                dataSummary.getTranscript().getSemesterYear(),
                                (List<ModuleGrade>) dataSummary.getTranscript().getGrades())
                        ).collect(Collectors.toList());

                DataDtoList dataDtoList = new DataDtoList(data);
                model.addAttribute("data", dataDtoList);
            }else {
                model.addAttribute("data", new ArrayList<ChartDto>());
            }



        }catch (WebClientResponseException.NotFound notFound){
            model.addAttribute("noChart", "Charts are empty");
        }

        return "editeChart";
    }

    @PostMapping(value = "editeChart")
    public String updateChart(@ModelAttribute DataDtoList data,
                              @RegisteredOAuth2AuthorizedClient("chart-client-authorization-code")
                              OAuth2AuthorizedClient authorizedClient){

        List<DataDto> dataDtoList = data.getDataDtoList();
        ChartDto chartDto = new ChartDto(dataDtoList.get(0).getChartId(), dataDtoList.get(0).getStudentId(), null, null);

        // delete empty rows from grades list

        List<DataSummary> dataSummaryList = dataDtoList.stream().map(dataDto -> {
            dataDto.getGrades().removeIf(grade-> (
                    grade.getGrade() == 0.0
                    && grade.getCredits() == 0
                    && (grade.getLabel() == null || grade.getLabel().isEmpty())
                    && grade.getModule() == null || grade.getModule().isEmpty()));
                Transcript transcript = new Transcript(dataDto.getSemester(), dataDto.getSemesterYear(), dataDto.getGrades(), 0, 0, 0.0);
                return new DataSummary(dataDto.getDataId(), dataDto.getChartId(), dataDto.getStudentId(), transcript);
        }).collect(Collectors.toList());

        if (dataSummaryList != null){
            ChartAggregate chartAggregate = new ChartAggregate(chartDto, dataSummaryList, null);

            //Make update request to backend
            try {
                URI uri = UriComponentsBuilder.fromUriString(CHART_SERVICE_URL).build().toUri();
                this.webClient
                        .patch()
                        .uri(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(chartAggregate), ChartAggregate.class)
                        .attributes(oauth2AuthorizedClient(authorizedClient))
                        .retrieve()
                        .bodyToMono(Void.class)
                        .block();
            }catch (ClientAuthorizationRequiredException e){
                LOG.warn(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }

        }
        return "redirect:/home";
    }
}
