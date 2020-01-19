# VacancyAggregator
The VacancyAggregator is a code for aggregating vacancies on different sites. It get vacancies from different sites then parses the pages and generates the common list.

For every site need to be a Strategy class describing the attributes for parsing vacancy pages.

There is a two strategies now in package:

1. For HeadHunter site.
2. For HabrCareer site.



There is a standart htmlView class, that generate an html page with aggregated vacancies. Bootstrap is linked.

If you need you can improve the code and get results as REST service or by deploying resulting html page on web-server, Tomcat for example.



## Requirements

You need to link JSoup library (org.jsoup.Jsoup)



## Not realized yet

In every strategy class there is a method to convert your search region  `getAreaCodeFromLocation(String location)`. This method is hardcoded now. Now there is a Moscow region only. 

For every strategy you will need to get full list and find your region and write your case in switch.

Full list for HeadHunter: [https://api.hh.ru/areas](https://api.hh.ru/areas)

Full list for HabrCareer you need to use its API.