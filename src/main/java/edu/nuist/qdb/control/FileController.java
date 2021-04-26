package edu.nuist.qdb.control;


import com.alibaba.fastjson.JSONObject;
import edu.nuist.qdb.entity.question.Question;
import edu.nuist.qdb.entity.question.QuestionAssemblor;
import edu.nuist.qdb.entity.question.QuestionService;
import edu.nuist.qdb.util.FileUploader;
import edu.nuist.qdb.xlsutil.Cell;
import edu.nuist.qdb.xlsutil.XLSReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
@CrossOrigin
@RestController
public class FileController {
    @Autowired
    private FileUploader fileUploader;

    @Autowired
    private QuestionService questionService;


    @PostMapping("/upload")

    public String upload(@RequestParam() MultipartFile file) {


          JSONObject obj = (JSONObject) fileUploader.singel(file, "xlshaha", "all").getObj();
          String fileName = (String) obj.get("inner");

        try {
            batchSave(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;

    }


    private List<Question> batchSave (String fileName) throws Exception {
        QuestionAssemblor qAssemblor = new QuestionAssemblor();
        XLSReader rr = new XLSReader("e:/xlshaha/" + fileName);
        List<List<Cell>> table = rr.read(0);
        List<Question> questions = new LinkedList<>();

        int index = 0;
        for (List<Cell> row: table) {
            try {
                if (index == 0) {
                    index++;
                    continue;
                }
                System.out.println(row);
                Question q = qAssemblor.exec(row);
                questionService.save(q);
                questions.add(q);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return questions;
    }



    }
