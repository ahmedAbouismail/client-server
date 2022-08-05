package htw.berlin.clientserver.clientserver.web.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.berlin.client.api.chart.ChartLabel;
import htw.berlin.client.api.chart.Transcript;
import htw.berlin.clientserver.clientserver.dto.ChartAggregate;
import htw.berlin.clientserver.clientserver.dto.GetChartAggregate;
import htw.berlin.clientserver.clientserver.dto.GradesChartDto;
import htw.berlin.clientserver.clientserver.dto.viewModel.Chart;
import htw.berlin.clientserver.clientserver.dto.viewModel.GradesChart;
import htw.berlin.clientserver.clientserver.dto.viewModel.ModuleChart;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
public class HomeController {
//    private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

    private static final  String CHART_SERVICE_URL = "http://localhost:8081/chart-composite";
    private final WebClient webClient;

    public HomeController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/")
    public String root(){
//        return "redirect:/index";

        int chartId = 1;
        String redirectUri = "redirect:/chart-composite/" + chartId;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String index(@RegisteredOAuth2AuthorizedClient("chart-client-authorization-code")
                                    OAuth2AuthorizedClient authorizedClient, Model model) {


            if (authorizedClient != null && !authorizedClient.getPrincipalName().isEmpty()) {
                URI uri = UriComponentsBuilder.fromUriString(CHART_SERVICE_URL + "/{studentId}").build(authorizedClient.getPrincipalName());

                try {
                    GetChartAggregate chart = this.webClient
                            .get()
                            .uri(uri)
                            .attributes(oauth2AuthorizedClient(authorizedClient))
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .bodyToMono(GetChartAggregate.class)
                            .block();
                    List<ChartAggregate> chartAggregateList = chart.getChartAggregates();

                    List<Chart> charts = prepareCharts(chartAggregateList);

//                    ObjectMapper objectMapper = new ObjectMapper();



//                    prepareCharts(chartAggregateList);
                    model.addAttribute("charts", charts);
                }catch (WebClientResponseException.NotFound notFound){
                    model.addAttribute("noChart", "Charts are empty");
                }


            }
        return "home";
    }

    private List<Chart> prepareCharts(List<ChartAggregate> chartAggregateList) {
        List<Chart> charts = new ArrayList<>();

        List<GradesChart> gradesCharts = getGradesCharts(chartAggregateList);
        gradesCharts.forEach(gradesChart -> charts.add(gradesChart));

        List<ModuleChart> modulesCharts = getModulesCharts(chartAggregateList);
        modulesCharts.forEach(moduleChart -> charts.add(moduleChart));



        //from gradesCharts we need average and semester
//        prepareGradesCharts(gradesCharts);

        return charts;
    }

    private void prepareGradesCharts(List<ChartAggregate> gradesCharts) {

        List<GradesChartDto> gradesChartDtoList = new ArrayList<>();
        gradesCharts.forEach(gradesChart -> {
            GradesChartDto gradesChartDto = new GradesChartDto();
            gradesChartDto.setChartDto(gradesChart.getChartSummary());
            gradesChart.getDataSummaryList().forEach(dataSummary -> {
                Transcript transcript = dataSummary.getTranscript();
                gradesChartDto.setData(List.of(
                        List.of(transcript.getSemester(), transcript.getAverage())
                ));
            });
        });
    }

    private List<ModuleChart> getModulesCharts(List<ChartAggregate> chartAggregateList) {
        List<ChartAggregate> chartAggregates = chartAggregateList.stream()
                .filter(chartAggregate -> chartAggregate.getChartSummary().getChartLabel() == ChartLabel.MODULES)
                .collect(Collectors.toList());

        List<ModuleChart> moduleCharts = chartAggregates.stream().map(chartAggregate ->
                new ModuleChart(
                        chartAggregate.getChartSummary().getChartId(),
                        chartAggregate.getChartSummary().getChartType(),
                        chartAggregate.getChartSummary().getChartLabel(),
                        chartAggregate.getDataSummaryList()
                )).collect(Collectors.toList());

        return moduleCharts;
    }

    private List<GradesChart> getGradesCharts(List<ChartAggregate> chartAggregateList) {
        List<ChartAggregate> chartAggregates = chartAggregateList.stream()
                .filter(chartAggregate -> chartAggregate.getChartSummary().getChartLabel() == ChartLabel.GRADES)
                .collect(Collectors.toList());

        List<GradesChart> gradesCharts = chartAggregates.stream().map(chartAggregate ->
                new GradesChart(
                        chartAggregate.getChartSummary().getChartId(),
                        chartAggregate.getChartSummary().getChartType(),
                        chartAggregate.getChartSummary().getChartLabel(),
                        chartAggregate.getDataSummaryList()
                )).collect(Collectors.toList());

        return gradesCharts;
    }


}


//    @GetMapping("/")
//    String index(Principal principal) {
//        return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
//    }