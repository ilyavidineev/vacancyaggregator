package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import vo.Vacancy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HHStrategy implements Strategy {
    private static final String SITENAME = "HeadHunter";
    private static final int PAGESLIMIT = 18;
    private static final String URL_FORMAT = "https://hh.ru/search/vacancy?text=%s&page=%d&area=%d";
    private int page = 0;

    @Override
    public List<Vacancy> getVacancies(String searchString, String location) {
        int areaCode = getAreaCodeFromLocation(location);
        int page = 0;
        List<Vacancy> vacancies = new ArrayList<>();
        Document document;
        try {
            document = getDocument(searchString, page, areaCode);
            while (true) {
                Elements elements = document.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy");
                if (elements.size() == 0) {
                    break;
                }
                for (Element element : elements) {
                    if (element != null && element.parent().hasClass("vacancy-serp")) {
                        Vacancy vacancy = new Vacancy();
                        vacancy.setTitle(element.getElementsByAttributeValueContaining("data-qa", "title").text());
                        vacancy.setCity(element.getElementsByAttributeValueContaining("data-qa", "address").text());
                        vacancy.setCompanyName(element.getElementsByAttributeValue("data-qa", "vacancy-serp__vacancy-employer").text());
                        vacancy.setSiteName(SITENAME);

                        String urlPage = element.getElementsByAttributeValueContaining("data-qa", "vacancy-serp__vacancy-title").attr("href");
                        vacancy.setUrl(urlPage);

                        String salary = element.getElementsByAttributeValueContaining("data-qa", "compensation").text();
                        vacancy.setSalary(salary.length() == 0 ? "" : salary);
                        vacancies.add(vacancy);
                    }
                }
                if (++page > PAGESLIMIT) break;
                document = getDocument(searchString, page, areaCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vacancies;
    }

    public int getAreaCodeFromLocation(String location) {
        //TODO: Realize area code returning from Location String
        switch (location.toLowerCase()) {
            case ("москва"):
                return 1;
            default:
                return 1;
        }
    }

    protected Document getDocument(String searchString, int page, int areaCode) throws IOException {
        String url = String.format(URL_FORMAT, searchString, page, areaCode);
        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/11.1.1 Safari/605.1.15")
                    .referrer("")
                    .timeout(3000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(page);
        }
        return document;
    }
}