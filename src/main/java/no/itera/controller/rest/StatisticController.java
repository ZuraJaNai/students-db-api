package no.itera.controller.rest;

import no.itera.model.StatisticData;
import no.itera.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("StatisticControllerRest")
@RequestMapping("/restapi/statistic")
public class StatisticController {

    StatisticService statisticService;

    @Autowired
    public void setStatisticService(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<StatisticData> getStatistic(){
        StatisticData data = statisticService.getStatisticData();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
