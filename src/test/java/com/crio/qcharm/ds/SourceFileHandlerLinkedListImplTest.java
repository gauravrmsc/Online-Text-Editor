package com.crio.qcharm.ds;

import com.crio.qcharm.log.UncaughtExceptionHandler;
import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.request.SearchRequest;
import com.crio.qcharm.request.UndoRequest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceFileHandlerLinkedListImplTest {

  @BeforeEach
  public void setupUncaughtExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
  }

  static FileInfo inefficientSearch;
  static List<Cursor> expectedCursorPositions = new ArrayList<>();
  static String pattern;

  private SourceFileHandlerLinkedListImpl getSourceFileHandler(String fileName) {
    return new SourceFileHandlerLinkedListImpl(fileName);
  }
  @BeforeAll
  static void setup() {

    StringBuffer prefix = new StringBuffer("");

    for (int i = 0; i < 30; ++i) {
      prefix.append("ab");
    }

    pattern = prefix.toString() + "aa";
    patternGenerator(prefix.toString() + "ab", pattern);
  }

  @AfterAll
  static void teardown() {
    inefficientSearch.getLines().clear();
  }

  static void patternGenerator(String pattern1, String pattern2) {
    StringBuffer buffer1 = new StringBuffer("");
    StringBuffer buffer2 = new StringBuffer("");

    int K = 250;
    for (int i = 0; i < K; ++i) {
      buffer1.append(pattern1);
    }
    buffer1.append(pattern2);

    for (int  i = 0; i < K; ++i) {
      buffer1.append(pattern1);
    }

    List<String> lines = new ArrayList<>();

    int N = 1000;

    String s1 = buffer1.toString();
    for (int i = 0; i < N; ++i) {
      lines.add(s1 + new Integer(i).toString() );
    }

    int len1 = pattern1.length();
    for (int i = 0; i < N; ++i) {
      expectedCursorPositions.add(new Cursor(i, K * len1));
    }

    inefficientSearch =  new FileInfo("testfile", lines);
  }

  FileInfo getLargeSampleFileInfo(String fileName, int n) {
    List<String> testLines = new ArrayList<>();

    for (int i = 0; i < n; ++i) {
      StringBuffer buffer = new StringBuffer("lineno");
      buffer.append(i);
      testLines.add(buffer.toString());
    }
    return new FileInfo(fileName, testLines);
  }
  
  @Test
  void largeFileLoadingReturnsFiftyLinesOfDataLinkedList() {
    SourceFileHandler sourceFileHandler = getSourceFileHandler("testfile");

    FileInfo fileInfo = getLargeSampleFileInfo("largeFile", 1000000);
    Page page = sourceFileHandler.loadFile(fileInfo);

    assertEquals(page.getLines(), fileInfo.getLines().subList(0, 50));
  }

  @Test
  void getNextLinesReturnsEmptyPageIfThereIsNoLinesAfter() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandler.loadFile(fileInfo);

    int length = 35;
    int startingLineNo = 100;
    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLineNo, fileName, length, cursor);
    Page emptyPage = sourceFileHandler.getNextLines(pageRequest);

    assertEquals(emptyPage.getLines(), new ArrayList<String>());
  }

  @Test
  void getNextLinesReturnsLessThanRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandler.loadFile(fileInfo);

    int length = 35;
    int startingLine = 90;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandler.getNextLines(pageRequest);

    assertEquals(page.getLines(), fileInfo.getLines().subList(startingLine+1, 100));
  }

  @Test
  void getNextLinesReturnsRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandler.loadFile(fileInfo);

    int length = 35;
    int startingLine = 35;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandler.getPrevLines(pageRequest);

    assertEquals(page.getLines(), fileInfo.getLines().subList(0, length));
  }

  @Test
  void getPrevLinesReturnsEmptyPageIfThereIsNoLinesBefore() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandler.loadFile(fileInfo);

    int length = 35;
    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(0, fileName, length, cursor);
    Page emptyPage = sourceFileHandler.getPrevLines(pageRequest);

    assertEquals(emptyPage.getLines(), new ArrayList<String>());
  }

  @Test
  void getPrevLinesReturnsLessThanRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandler.loadFile(fileInfo);

    int length = 35;
    int startingLine = 10;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandler.getPrevLines(pageRequest);

    assertEquals(page.getLines(), fileInfo.getLines().subList(0, 10));
  }

  @Test
  void getPrevLinesReturnsRequestedNumberOfLinesLinked() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandler.loadFile(fileInfo);

    int length = 35;
    int startingLine = 35;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandler.getPrevLines(pageRequest);

    assertEquals(page.getLines(), fileInfo.getLines().subList(0, length));
  }

  @Test
  void getLinesFromReturnsRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandler.loadFile(fileInfo);

    int length = 35;
    int startingLine = 0;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandler.getLinesFrom(pageRequest);

    assertEquals(page.getLines(), fileInfo.getLines().subList(startingLine, length));
  }

  @Test
  void efficientSearchTest() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    sourceFileHandler.loadFile(inefficientSearch);
    SearchRequest searchRequest = new SearchRequest(0, pattern, fileName);
    long timeTakenInMs = 0;
    System.currentTimeMillis();
    for (int i = 0; i < 10; ++i) {
      long startTime = System.currentTimeMillis();
      List<Cursor> cursors = sourceFileHandler.search(searchRequest);
      timeTakenInMs += System.currentTimeMillis() - startTime;
      assertEquals(expectedCursorPositions, cursors);
    }
    System.out.println(timeTakenInMs);
    assert (timeTakenInMs < 2000);
  }

  @Test
  void search() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    int N = 100;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandler.loadFile(fileInfo);

    SearchRequest searchRequest = new SearchRequest(0, "lineno", fileName);

    List<Cursor> cursors = sourceFileHandler.search(searchRequest);
    List<Cursor> expected = new ArrayList<>();

    for (int i = 0; i < N; ++i) {
      expected.add(new Cursor(i, 0));
    }

    assertEquals(expected, cursors);
  }


  @Test
  void editLines() {
    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    int N = 100;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandler.loadFile(fileInfo);

    List<String> changedLines = new ArrayList<>();
    for (int i = 0; i < 35; ++i) {
      StringBuffer buffer = new StringBuffer("LineNumber");
      buffer.append(i);
      changedLines.add(buffer.toString());
    }
    
    EditRequest editRequest = new EditRequest(35, 70, changedLines, fileName, new Cursor(0,0));
    sourceFileHandler.editLines(editRequest);

    PageRequest pageRequest = new PageRequest(0, fileName, N, new Cursor(0,0));
    Page page = sourceFileHandler.getLinesFrom(pageRequest);

    assertEquals(page.getLines().subList(0,35), fileInfo.getLines().subList(0, 35));
    assertEquals(page.getLines().subList(35, 70), changedLines);
    assertEquals(page.getLines().subList(70,N), fileInfo.getLines().subList(70, N));
  }


  @Test
  void linkedListEditLinesShouldBeEfficient() {

    String fileName = "testfile";
    SourceFileHandler sourceFileHandler = getSourceFileHandler(fileName);

    int N = 10000;
    int K = 1000;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandler.loadFile(fileInfo);

    List<String> changedLines = new ArrayList<>();
    for (int i = 0; i < 1; ++i) {
      StringBuffer buffer = new StringBuffer("LineNumber");
      buffer.append(i);
      changedLines.add(buffer.toString());
    }

    Cursor cursorAt = new Cursor(0, 0);
    EditRequest editRequest = new EditRequest(0, 0, changedLines, fileName, cursorAt);
    PageRequest pageRequest = new PageRequest(0, fileName, 1, cursorAt);

    long startTime = System.currentTimeMillis();
    for (int i = 0; i < K; ++i) {
      sourceFileHandler.editLines(editRequest);

      Page pageResponse = sourceFileHandler.getLinesFrom(pageRequest);
      assertEquals(pageResponse.getLines(), changedLines);
    }
    long timeTaken = System.currentTimeMillis() - startTime;

    System.out.println(timeTaken);
    assert(timeTaken < 500);
    PageRequest pageRequestNew = new PageRequest(0, fileName, N + K, cursorAt);
    Page page = sourceFileHandler.getLinesFrom(pageRequestNew);

    assertEquals(page.getLines().subList(K, K + N), fileInfo.getLines());
  }




}

