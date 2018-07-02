package no.itera.services;

import no.itera.dao.PersonDao;
import no.itera.model.StatisticData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    PersonDao personDao;

    @Override
    public StatisticData getStatisticData() {
        StatisticData data = new StatisticData();
        data.setTotalNumberOfPersons(this.getNumberOfPersons());
        data.setNumberOfPersonsByYear(this.getPersonsInAllYears());
        data.setNumberOfPersonsWithInternshipNow(this.getNumberOfPersonsCurrentlyOnInternship());
        data.setNumberOfPersonsWithPracticeNow(this.getNumberOfPersonsCurrentlyOnPractice());
        data.setNumberOfPersonsWithJobNow(this.getNumberOfPersonsCurrentlyOnJob());
        data.setNumberOfPersonsWithInternship(this.getNumberOfPersonsWithInternship());
        data.setNumberOfPersonsWithPractice(this.getNumberOfPersonsWithPractice());
        data.setNumberOfPersonsWithJob(this.getNumberOfPersonsWithJob());
        return data;
    }

    private Integer getNumberOfPersons(){
        return Math.toIntExact(personDao.count());
    }

    private Map<String,Integer> getPersonsInAllYears(){
        List<String> years = personDao.findDistinctYearsOfStudy();
        Map<String,Integer> personsByYears = new HashMap<>();
        for (String year: years) {
            personsByYears.put(year,this.getNumberOfPersonsInYear(year));
        }
        return personsByYears;
    }
    private Integer getNumberOfPersonsInYear(String year){
        return personDao.countAllByYearOfStudy(year);
    }

    private Integer getNumberOfPersonsCurrentlyOnInternship(){
        return personDao.countAllByInternshipBeginIsNotNullAndInternshipEndIsNull();
    }

    private Integer getNumberOfPersonsCurrentlyOnPractice(){
        return personDao.countAllByPracticeBeginIsNotNullAndPracticeEndIsNull();
    }

    private Integer getNumberOfPersonsCurrentlyOnJob(){
        return personDao.countAllByJobBeginIsNotNullAndJobEndIsNull();
    }

    private Integer getNumberOfPersonsWithInternship(){
        return personDao.countAllByInternshipBeginIsNotNull();
    }

    private Integer getNumberOfPersonsWithPractice(){
        return personDao.countAllByPracticeBeginIsNotNull();
    }

    private Integer getNumberOfPersonsWithJob(){
        return personDao.countAllByJobBeginIsNotNull();
    }
}
