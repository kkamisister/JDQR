package com.example.backend.table.service;

import static com.example.backend.table.dto.TableRequest.*;


import com.example.backend.common.dto.CommonResponse.ResponseWithData;

public interface TableService {

	ResponseWithData<String> createTable(TableInfo tableInfo,String userCode);

}
