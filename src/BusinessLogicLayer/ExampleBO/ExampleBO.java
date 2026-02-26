package BusinessLogicLayer.ExampleBO;


import DAOLayer.DAOFacade;
import DAOLayer.ExampleDAO;
import DTOLayer.ExampleDTO;

import java.util.List;

public class ExampleBO implements IExampleBO {

    private final DAOFacade exampleDAO;

    // Dependency Injection: only ExampleDAO
    public ExampleBO(DAOFacade exampleDAO) {
        this.exampleDAO = exampleDAO;
    }

    @Override
    public int addExample(ExampleDTO example) {
        return exampleDAO.addExample(example);
    }

    @Override
    public ExampleDTO getExampleById(int id) {
        return exampleDAO.getExampleById(id);
    }

    @Override
    public List<ExampleDTO> getExamplesByWordId(int wordId) {
        return exampleDAO.getExamplesByWordId(wordId);
    }

    @Override
    public boolean updateExample(ExampleDTO example) {
        return exampleDAO.updateExample(example);
    }

    @Override
    public boolean deleteExample(int id) {
        return exampleDAO.deleteExample(id);
    }
    public List<ExampleDTO> getExamplesByText(String text)
    {
        return exampleDAO.getExamplesByText(text );
    }
    public List<ExampleDTO> getAllExamples()
    {
        return exampleDAO.getAllExamples() ;
    }
}