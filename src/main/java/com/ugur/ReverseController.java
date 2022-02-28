package com.ugur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.*;


@Controller
public class ReverseController {
    List<VerschlimmerungForm> newList = new ArrayList<>();


    @Bean
    public FlywayMigrationStrategy repairFlyway() {
        return flyway -> {
            // repair each script's checksum
            flyway.repair();
            // before new migrations are executed
            flyway.migrate();
        };
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/")
    public String getProblem(Model model) {
        model.addAttribute("saveProblemForm", new ProblemForm());
        return "problemForm";
    }

    @PostMapping("/")
    public String saveProblem(Model model, ProblemForm problemForm) {

        model.addAttribute("saveProblemForm", new ProblemForm());
        jdbcTemplate.update("INSERT INTO PROBLEM VALUES (?,?)",problemForm.getId(), problemForm.getDescription());
        String queryAllProblems = "SELECT * FROM PROBLEM";
        List<ProblemForm> problemFormList = jdbcTemplate.query(queryAllProblems, new ProblemRowMapper());

        return "redirect:/verschlimm";
    }

    @GetMapping("/verschlimm")
    public String getVerschlimmerungForm(Model model, VerschlimmerungForm verschlimmerungForm, ProblemForm problemForm) {
        model.addAttribute("saveVerschlimmerungForm", new VerschlimmerungForm());
        return "/verschlimmerungForm";
    }

    @PostMapping("/verschlimm")
    public String saveVerschlimmerungForm(Model model, ProblemForm problemForm, VerschlimmerungForm verschlimmerungForm) {
        model.addAttribute("saveVerschlimmerungForm", new VerschlimmerungForm());
        VerschlimmerungForm form = new VerschlimmerungForm();
        form.setProblem_id(1);
        jdbcTemplate.update("INSERT INTO WORSENING VALUES (?,?,?)", verschlimmerungForm.getId(), verschlimmerungForm.getDescription(), form.getProblem_id());
        String queryAllVerschlimmerung = "SELECT * FROM WORSENING";
        List<VerschlimmerungForm> verschlimmerungFormList = jdbcTemplate.query(queryAllVerschlimmerung, new VerschlimmerungRowMapper());

        newList = verschlimmerungFormList;
        System.out.println(verschlimmerungFormList);
        return "verschlimmerungForm";
    }


    @GetMapping("/losung")
    public String getLosungForm(Model model, VerschlimmerungForm verschlimmerungForm) {

        model.addAttribute("saveLosungForm", new LosungForm());
        model.addAttribute("verschlimmerungForm", verschlimmerungForm);
        model.addAttribute("newList",newList);

        //     problemFormList.get(verschlimmerungForm.getIndexOfProblem()).getVerschlimm().add(verschlimmerungForm);
        //    verschlimmerungFormList.get(verschlimmerungFormList.getIndexOfVerschlimmerung()).getLosung().add(LosungForm)
        return "losungForm";
    }

    @PostMapping("/losung")
    public String getLosungForm(Model model, LosungForm losungForm) {
        model.addAttribute("newList", newList);
        model.addAttribute("saveLosungForm", new LosungForm());
      /*  losungFormList.add(losungForm);
        model.addAttribute("losungFormList", losungFormList);
        verschlimmerungFormList.get(losungForm.getIndexOfVerschlimmerung()).getLosungen().add(losungForm);
        // System.out.println(verschlimmerungFormList.get(losungForm.getIndexOfVerschlimmerung()));


       */
        return "losungForm";
    }
}


























  /*
        SimpleJdbcInsert simpleJdbcInsert;

        @Autowired
        public ReverseController(DataSource dataSource) {
            simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                    .withTableName("PROBLEM").usingGeneratedKeyColumns("id");
        }

        public long insertProblem(String problemText) {
            Map<String, Object> parameters = new HashMap<>(1);
            parameters.put("description", problemText);
            Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
            return (long) newId;
        }
    */

// --------------------------------------------------------------------------------------------------------------------------------------

        /*
        long id = insertProblem(problemForm.getDescription());

        jdbcTemplate.update("INSERT INTO PROBLEM VALUES (?,?)",problemForm.getId(), problemForm.getDescription());

        model.addAttribute("saveProblemForm", new ProblemForm());

        ProblemForm singleProblem = jdbcTemplate.queryForObject("SELECT * FROM PROBLEM WHERE ID = ?", new ProblemRowMapper(), id);

        System.out.println(singleProblem);
          long id = insertProblem(problemForm.getDescription());
        id = problemForm.getId();
        ProblemForm singleProblemForm = jdbcTemplate.queryForObject("SELECT * FROM PROBLEM WHERE ID = ?", new ProblemRowMapper(), id);
         model.addAttribute("singleProblem", singleProblemForm);
         model.addAttribute("problemFormList", new ProblemForm());


        // System.out.println(insertProblem(problemForm));
        //return "redirect:/verschlimm";
*/

// --------------------------------------------------------------------------------------------------------------------------------------

/*
public static List<VerschlimmerungForm> verschlimmerungFormList = new ArrayList<>();
    //    public static List<ProblemForm> problemFormList = new ArrayList<>();
    public static List<LosungForm> losungFormList = new ArrayList<>();

    private String problem;

    @GetMapping("/")
    public String getProblemForm(Model model) {
        model.addAttribute("saveProblemForm", new ProblemForm());
        return "problemForm";
    }

    @PostMapping("/saveProblemForm")
    public String saveProblemForm(Model model, String problemForm) {
        model.addAttribute("saveProblemForm", new ProblemForm());
        problem = problemForm;
        System.out.println(problem);
        return "redirect:/verschlimm";
    }

    @GetMapping("/verschlimm")
    public String getVerschlimmerungForm(Model model) {
        model.addAttribute("saveVerschlimmerungForm", new VerschlimmerungForm());
        model.addAttribute("problem", problem);
        return "verschlimmerungForm";
    }

    @PostMapping("/verschlimm")
    public String saveVerschlimmerungForm(Model model, VerschlimmerungForm verschlimmerungForm, ProblemForm problemForm) {
        model.addAttribute("saveVerschlimmerungForm", new VerschlimmerungForm());
        model.addAttribute("problemForm", problemForm);
        verschlimmerungFormList.add(verschlimmerungForm);

        return "verschlimmerungForm";
    }

    @GetMapping("/losung")
    public String getLosungForm(Model model, VerschlimmerungForm verschlimmerungForm) {
        model.addAttribute("verschlimmerungFormList", verschlimmerungFormList);
        model.addAttribute("saveLosungForm", new LosungForm());
        model.addAttribute("verschlimmerungForm", verschlimmerungForm);


        //     problemFormList.get(verschlimmerungForm.getIndexOfProblem()).getVerschlimm().add(verschlimmerungForm);
        //    verschlimmerungFormList.get(verschlimmerungFormList.getIndexOfVerschlimmerung()).getLosung().add(LosungForm)
        return "losungForm";
    }

    @PostMapping("/losung")
    public String getLosungForm(Model model, LosungForm losungForm) {
        model.addAttribute("verschlimmerungFormList", verschlimmerungFormList);
        model.addAttribute("saveLosungForm", new LosungForm());
        losungFormList.add(losungForm);
        model.addAttribute("losungFormList", losungFormList);
        verschlimmerungFormList.get(losungForm.getIndexOfVerschlimmerung()).getLosungen().add(losungForm);
        // System.out.println(verschlimmerungFormList.get(losungForm.getIndexOfVerschlimmerung()));

        return "losungForm";
    }

    @GetMapping("/ansicht")
    public String showSeite(Model model, LosungForm losungForm) {
        model.addAttribute("verschlimmerungFormList", verschlimmerungFormList);
        model.addAttribute("saveLosungForm", new LosungForm());
        losungFormList.add(losungForm);
        model.addAttribute("losungFormList", losungFormList);
        verschlimmerungFormList.get(losungForm.getIndexOfVerschlimmerung()).getLosungen().add(losungForm);

        return "ansicht";
    }

 */