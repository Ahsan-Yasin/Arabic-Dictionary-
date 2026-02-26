package BusinessLogicLayer.ExampleBO;

import DTOLayer.ExampleDTO;

import java.util.List;

public interface IExampleBO {
    int addExample(ExampleDTO example);
    ExampleDTO getExampleById(int id);
    List<ExampleDTO> getExamplesByWordId(int wordId);
    boolean updateExample(ExampleDTO example);
    boolean deleteExample(int id);

    List<ExampleDTO> getExamplesByText(String text);

    List<ExampleDTO> getAllExamples();
}