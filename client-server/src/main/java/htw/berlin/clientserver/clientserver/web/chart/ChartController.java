package htw.berlin.clientserver.clientserver.web.chart;

import com.fasterxml.jackson.databind.ObjectMapper;
import htw.berlin.client.api.chart.*;
import htw.berlin.clientserver.clientserver.dto.ChartAggregationDto;
import htw.berlin.clientserver.clientserver.dto.ChartDto;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;


@Controller
public class ChartController{

    private static final Logger LOG = LoggerFactory.getLogger(ChartController.class);

    private static final  String CHART_SERVICE_URL = "http://localhost:8081/chart-composite";
    private static final  String CHART_SERVICE_DELETE_URL = "http://localhost:8081/chart-composite";
    private static final  String CREATE_CHART_SERVICE_URL = "http://localhost:8081/chart-composite/createChart";

    private final WebClient webClient;
    private final ObjectMapper mapper;


    @Autowired
    public ChartController(WebClient webClient, ObjectMapper mapper) {
        this.mapper = mapper;
        this.webClient = webClient;
    }


    @GetMapping(value = "/test")
    public String createText(){
        return "home";
    }

    @GetMapping(value = "/create")
    public String getNewChart(Model model){
        ChartDto chartDto = new ChartDto();

        model.addAttribute("chart", chartDto);
        return "newChart";
    }

    @PostMapping(value = "/create")
    private String createNewChart(@ModelAttribute ChartDto chartDto,
                                  @RegisteredOAuth2AuthorizedClient("chart-client-authorization-code")
                                  OAuth2AuthorizedClient authorizedClient,
                                  Model model){

        String chartId = UUID.randomUUID().toString();
        chartDto.setChartId(chartId);
        chartDto.setStudentId(authorizedClient.getPrincipalName());

        // Send Chart to resource server
        try {
            this.webClient.post()
                    .uri(CREATE_CHART_SERVICE_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(chartDto), ChartDto.class)
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

    @GetMapping (value = "/deleteChart")
    private String deleteChart(@RequestParam String chartId,
                               @RegisteredOAuth2AuthorizedClient("chart-client-authorization-code")
                               OAuth2AuthorizedClient authorizedClient){
        URI uri = UriComponentsBuilder.fromUriString(CHART_SERVICE_DELETE_URL + "/{chartId}").build(chartId);
        try {
            this.webClient.delete()
                    .uri(uri)
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

    @PostMapping(value = "/chart-composite")
    public void createChart(@RegisteredOAuth2AuthorizedClient("chart-client-authorization-code")
                            OAuth2AuthorizedClient authorizedClient) {
//        ModuleGrade moduleGrade = new ModuleGrade(1.2, "Mathe", "Mathe2", 5);
//        Transcript transcript = new Transcript("ss22", Collections.singletonList(moduleGrade), 0, 0.0);
//        DataSummery dataSummery = new DataSummery(1, "s05", transcript);
//        ChartAggregate chartAggregate = new ChartAggregate(1, "s05", ChartLabel.MODULES, ChartType.LINE, dataSummery, null );
//
//        try {
//                this.webClient.post()
//                .uri(CHART_SERVICE_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Mono.just(chartAggregate), ChartAggregate.class)
//                .attributes(oauth2AuthorizedClient(authorizedClient))
//                .retrieve()
//                .bodyToMono(Void.class)
//                .block();
//        }catch (ClientAuthorizationRequiredException e){
//            LOG.warn(e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }

    }

    @GetMapping(value = "/chart-composite/{chartId}")
    public ChartAggregate getChart(@PathVariable int chartId,
                                   @RegisteredOAuth2AuthorizedClient("chart-client-authorization-code")
                                   OAuth2AuthorizedClient authorizedClient) {
        URI uri = UriComponentsBuilder.fromUriString(CHART_SERVICE_URL + "/{chartId}").build(chartId);
        ChartAggregate chart = this.webClient
        .get()
        .uri(uri)
        .attributes(oauth2AuthorizedClient(authorizedClient))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(ChartAggregate.class)
        .block();

        return chart;
    }

    public ChartAggregate updateChart(ChartAggregate body) {
        return null;
    }

    @RequestMapping(value = "/chart-composite-delete/{chartId}", method = RequestMethod.DELETE)
    public void deleteChart(@PathVariable int chartId,
                            @RegisteredOAuth2AuthorizedClient("chart-client-authorization-code")
                            OAuth2AuthorizedClient authorizedClient) {
        URI uri = UriComponentsBuilder.fromUriString(CHART_SERVICE_URL + "/{chartId}").build(chartId);

        this.webClient.delete()
                .uri(uri)
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

}
