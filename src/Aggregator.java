import controller.Controller;
import model.HHStrategy;
import model.Model;
import model.HabrCareerStrategy;
import model.Provider;
import view.HtmlView;

//TODO:
//1. добавить еще сайтов для агрегирования вакансий (создать стратегию, а потом добавить в модель провайдер с этой стратегией)
//2. отсортировать все вакансии, по дате создания (распарсить дату в полученном html).
//3. создать свою вью, на свинге. Подменить в main HtmlView на SwingView.
//Подключать либы, сделать, чтоб запрос приходил со swing-формы.
//4. собрать приложение в war-ник, развернуть Томкат, задеплоить приложение туда. Сделать, чтоб запрос приходил с браузера.

public class Aggregator {
    public static void main(String[] args) {
        HtmlView view = new HtmlView();
        Provider hhProvider = new Provider(new HHStrategy());
        Provider mkProvider = new Provider(new HabrCareerStrategy());

        Model model = new Model(view, hhProvider, mkProvider);
        Controller controller = new Controller(model);
        view.setController(controller);

        //TODO: Realize area select
        String location = "Москва"; // mocked Moscow
        view.userCitySelectEmulationMethod(location);

    }

}
