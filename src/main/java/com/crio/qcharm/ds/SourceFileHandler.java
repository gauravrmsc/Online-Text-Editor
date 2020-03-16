package com.crio.qcharm.ds;

import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.request.SearchRequest;
import com.crio.qcharm.request.UndoRequest;
import java.util.List;

public interface SourceFileHandler {

  SourceFileVersion getLatestSourceFileVersion(String fileName);

  Page loadFile(FileInfo fileInfo);

  Page getPrevLines(PageRequest pageRequest);

  Page getNextLines(PageRequest pageRequest);

  Page getLinesFrom(PageRequest pageRequest);

  List<Cursor> search(SearchRequest searchRequest);


  void editLines(EditRequest editRequest);



}
