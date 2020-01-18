package model;

import vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerStrategy implements Strategy {
    private static final String SITENAME = "HabrCareer";

    // https://career.habr.com/vacancies?q=java&currency=rur&city_id=678&type=all
    private static final String URL_FORMAT = "https://career.habr.com/vacancies?q=%s&currency=rur&page=%d&city_id=%d";

    @Override
    public List<Vacancy> getVacancies(String searchString, String location) {
        int areaCode = getAreaCodeFromLocation(location);
        List<Vacancy> Vacancies = new ArrayList<>();
        int pageNum = 0;
        Document doc = null;
        while (true) {
            try {
                doc = getDocument(searchString, pageNum, areaCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements vacancies = doc.getElementsByClass("job");
            if (vacancies.size() == 0) break;
            for (Element element : vacancies) {
                if (element != null) {
                    Vacancy vacancy = new Vacancy();
                    vacancy.setTitle(element.getElementsByAttributeValue("class", "title").text());
                    vacancy.setCompanyName(element.getElementsByAttributeValue("class", "company_name").text());
                    vacancy.setSiteName(SITENAME);
                    vacancy.setUrl("https://career.habr.com" + element.select("a[class=job_icon]").attr("href"));

                    String salary = element.getElementsByAttributeValue("class", "salary").text();
                    vacancy.setSalary(salary.length() == 0 ? "" : salary);

                    String city = element.getElementsByAttributeValue("class", "location").text();
                    vacancy.setCity(city.length() == 0 ? "" : city);

                    Vacancies.add(vacancy);
                }
            }
            pageNum++;
        }
        return Vacancies;
    }

    @Override
    public int getAreaCodeFromLocation(String location) {
        //TODO: Realize area code returning from Location String
        switch (location.toLowerCase()) {
            case ("москва"):
                return 678; // Moscow area code
            default:
                return 0;
        }
    }

    protected Document getDocument(String searchString, int page, int areaCode) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page, areaCode);
        return Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36")
                .timeout(3000)
                .referrer("http://google.ru")
                .get();
    }
}