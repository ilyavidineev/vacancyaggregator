package model;

import view.View;
import vo.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private View view;
    private Provider[] providers;

    public Model(View view, Provider... providers) throws IllegalArgumentException {
        if (view == null || providers == null || providers.length == 0) {
            throw new IllegalArgumentException("Illegal arguments");
        }
        this.view = view;
        this.providers = providers;
    }


    public void selectCity(String location) {
        List<Vacancy> vacancies = new ArrayList<>();
        for (Provider p : providers) {
            vacancies.addAll(p.getJavaVacancies(location));
        }
        view.update(vacancies);
    }
}
