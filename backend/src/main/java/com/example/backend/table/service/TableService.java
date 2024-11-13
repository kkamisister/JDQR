package com.example.backend.table.service;

import static com.example.backend.table.dto.TableRequest.*;
import static com.example.backend.table.dto.TableResponse.*;

import com.example.backend.common.dto.CommonResponse.ResponseWithData;
import com.example.backend.table.dto.TableResponse;

public interface TableService {

	void createTable(TableInfo tableInfo,Integer userId);
	void deleteTable(String tableId,Integer userId);
	void updateTable(TableInfo tableInfo,Integer userId);
	QRInfo createQR(String tableId,Integer userId);
	TableResultDto getAllTables(Integer userId);
	TableDetailInfo getTable(String tableId,Integer userId);

	TableNameDto getTableName(String tableId);
}
