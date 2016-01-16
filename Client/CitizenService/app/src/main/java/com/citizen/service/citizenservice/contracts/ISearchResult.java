package com.citizen.service.citizenservice.contracts;

import com.citizen.service.citizenservice.models.IssueListItemModel;

import java.util.List;

public interface ISearchResult {
    void setResultData(List<IssueListItemModel> models);
}
