package Controller;

import Entities.Base.Entity;

import java.util.List;

public interface BaseController<T> {
    void HomePage();
    void SortPage(List<T> list);
    Entity InputPage();
    void AddPage();
    void ViewPage();
    void UpdatePage();
    void DeletePage();
    void SearchPage();
}
