package com.crio.qcharm.ds;

import com.crio.qcharm.log.UncaughtExceptionHandler;
import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.request.SearchRequest;
import com.crio.qcharm.request.UndoRequest;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceFileHandlerLinkedListImplTest {

  @BeforeEach
  public void setupUncaughtExceptionHandler() {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler());
    ThreadContext.put("runId", UUID.randomUUID().toString());
  }

  static FileInfo inefficientSearch;
  static List<Cursor> expectedCursorPositions = new LinkedList<>();
  static String pattern;
  @BeforeAll
  static void setup() {

    StringBuffer prefix = new StringBuffer("");

    for (int i = 0; i < 30; ++i) {
      prefix.append("ab");
    }

    pattern = prefix.toString() + "aa";
    patternGenerator(prefix.toString() + "ab", pattern);
  }

  List<String> clone(List<String> lst) {
    return lst.stream().collect(Collectors.toList());
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

    List<String> lines = new LinkedList<>();

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
    List<String> testLines = new LinkedList<>();

    for (int i = 0; i < n; ++i) {
      StringBuffer buffer = new StringBuffer("lineno");
      buffer.append(i);
      testLines.add(buffer.toString());
    }
    return new FileInfo(fileName, testLines);
  }

  FileInfo getSampleFileInfo() {
    List<String> testLines = new LinkedList<>();
    testLines.add("def sqr(x):");
    testLines.add(" return x * x");

    return new FileInfo("testfile.txt", testLines);
  }

  @Test
  void smallFileLoadingReturnsAllLines() {
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList("testfile");

    FileInfo fileInfo = getSampleFileInfo();
    Page page = sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    assertEquals(fileInfo.getLines(), page.getLines());
  }

  private SourceFileHandlerLinkedListImpl getSourceFileHandlerLinkedList(String testfile) {
    return new SourceFileHandlerLinkedListImpl(testfile);
  }

  @Test
  void largeFileLoadingReturnsFiftyLinesOfData() {
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList("testfile");

    FileInfo fileInfo = getLargeSampleFileInfo("largeFile", 1000000);
    Page page = sourceFileHandlerLinkedListImpl.loadFile(fileInfo);
    assertEquals(fileInfo.getLines().subList(0, 50), page.getLines());
    assertEquals(0, page.getStartingLineNo());
    assertEquals(new Cursor(0,0), page.getCursorAt());
  }

  @Test
  void getNextLinesReturnsEmptyPageIfThereIsNoLinesAfter() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    int length = 35;
    int startingLineNo = 100;
    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLineNo, fileName, length, cursor);
    Page emptyPage = sourceFileHandlerLinkedListImpl.getNextLines(pageRequest);

    assertEquals(new LinkedList<String>(), emptyPage.getLines());
    assertEquals(100, emptyPage.getStartingLineNo());
    assertEquals(cursor, emptyPage.getCursorAt());
  }

  @Test
  void getNextLinesReturnsLessThanRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    int length = 35;
    int startingLine = 90;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerLinkedListImpl.getNextLines(pageRequest);

    assertEquals(fileInfo.getLines().subList(startingLine+1, 100), page.getLines());
    assertEquals(startingLine+1, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  void getPrevLinesReturnsRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    int length = 35;
    int startingLine = 35;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerLinkedListImpl.getPrevLines(pageRequest);

    assertEquals(fileInfo.getLines().subList(0, length), page.getLines());
    assertEquals(0, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  void getPrevLinesReturnsEmptyPageIfThereIsNoLinesBefore() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    int length = 35;
    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(0, fileName, length, cursor);
    Page emptyPage = sourceFileHandlerLinkedListImpl.getPrevLines(pageRequest);

    assertEquals(new LinkedList<String>(), emptyPage.getLines());
  }

  @Test
  void getPrevLinesReturnsLessThanRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    int length = 35;
    int startingLine = 10;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerLinkedListImpl.getPrevLines(pageRequest);

    assertEquals(fileInfo.getLines().subList(0, 10), page.getLines());
    assertEquals(0, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  void getNextLinesReturnsRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    int length = 35;
    int startingLine = 35;

    Cursor cursor = new Cursor(0, 0);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerLinkedListImpl.getNextLines(pageRequest);

    assertEquals(fileInfo.getLines().subList(36, 71), page.getLines());
    assertEquals(36, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  void getLinesFromReturnsRequestedNumberOfLines() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    FileInfo fileInfo = getLargeSampleFileInfo(fileName, 100);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    int length = 35;
    int startingLine = 10;

    Cursor cursor = new Cursor(20, 13);
    PageRequest pageRequest = new PageRequest(startingLine, fileName, length, cursor);
    Page page = sourceFileHandlerLinkedListImpl.getLinesFrom(pageRequest);

    Cursor expectedCursorPosition = new Cursor(startingLine, 0);
    assertEquals(expectedCursorPosition, page.getCursorAt());
    assertEquals(fileInfo.getLines().subList(startingLine, startingLine + length), page.getLines());
    assertEquals(10, page.getStartingLineNo());
  }

  @Test
  void  efficientSearchTest() {
    String fileName = "efficientSearchTest";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    sourceFileHandlerLinkedListImpl.loadFile(inefficientSearch);
    SearchRequest searchRequest = new SearchRequest(0, pattern, fileName);
    long timeTakenInNs = 0;
    for (int i = 0; i < 10; ++i) {
      long startTime = System.nanoTime();
      List<Cursor> cursors = sourceFileHandlerLinkedListImpl.search(searchRequest);
      timeTakenInNs += System.nanoTime() - startTime;
      assertEquals(expectedCursorPositions, cursors);
    }
    System.out.printf("efficientSearchTest timetaken = %d ns\n", timeTakenInNs);
    assert (timeTakenInNs < 3000000000l);
  }



  @Test
  void randomJumpResultsInBadPerformance() {
    String fileName = "jump.txt";
    SourceFileHandler sourceFileHandler = getSourceFileHandlerLinkedList(fileName);

    int N = 250000;
    final FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandler.loadFile(fileInfo);

    List<Integer> offsets = new LinkedList<>();

    int incr = N / 10000;
    for (int i = 0; i < 10000; ++i) {
      //offsets.add((int)(Math.random() * N));
      int idx = i * incr;
      offsets.add(idx);
    }

    long timeTakenInNs = 0;
    for (int times = 0; times < 100; ++times) {
      Collections.shuffle(offsets);
      for (int i = 0; i < offsets.size(); ++i) {
        int start = offsets.get(i);
        final int numberOfLines = 5;
        final PageRequest pageRequest = new PageRequest(start, fileName, numberOfLines,
            new Cursor(i,
                0));
        long startTime = System.nanoTime();
        final Page page = sourceFileHandler.getLinesFrom(pageRequest);
        timeTakenInNs += System.nanoTime() - startTime;

        int end = Math.min(numberOfLines + start, fileInfo.getLines().size());
        assertEquals(fileInfo.getLines().subList(start, end), page.getLines());
      }
    }
    System.out.println(timeTakenInNs);
    assert(timeTakenInNs < 3000000000l);
  }

  @Test
  void search() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    int N = 100;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    SearchRequest searchRequest = new SearchRequest(0, "lineno", fileName);

    List<Cursor> cursors = sourceFileHandlerLinkedListImpl.search(searchRequest);
    List<Cursor> expected = new LinkedList<>();

    for (int i = 0; i < N; ++i) {
      expected.add(new Cursor(i, 0));
    }

    assertEquals(expected, cursors);
  }



  @Test
  void editLines() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    int N = 100;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    List<String> changedLines = new LinkedList<>();
    for (int i = 0; i < 35; ++i) {
      StringBuffer buffer = new StringBuffer("LineNumber");
      buffer.append(i);
      changedLines.add(buffer.toString());
    }

    EditRequest editRequest = new EditRequest(35, 70, changedLines, fileName, new Cursor(0,0));
    sourceFileHandlerLinkedListImpl.editLines(editRequest);

    PageRequest pageRequest = new PageRequest(0, fileName, N, new Cursor(0,0));
    Page page = sourceFileHandlerLinkedListImpl.getLinesFrom(pageRequest);

    assertEquals(fileInfo.getLines().subList(0, 35), page.getLines().subList(0,35));
    assertEquals(changedLines, page.getLines().subList(35, 70));
    assertEquals(fileInfo.getLines().subList(70, N), page.getLines().subList(70,N));
  }


  @Test
  void insertLinesAtTop() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    int N = 10;
    int K = 3;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    List<String> changedLines = new LinkedList<>();
    for (int i = 0; i < K; ++i) {
      StringBuffer buffer = new StringBuffer("LineNumber");
      buffer.append(i);
      changedLines.add(buffer.toString());
    }
    List<String> newContents = new LinkedList<>();

    newContents.addAll(changedLines);
    newContents.addAll(fileInfo.getLines());

    Cursor cursor = new Cursor(0, 0);
    EditRequest editRequest = new EditRequest( 0, N, newContents, fileName, cursor);
    sourceFileHandlerLinkedListImpl.editLines(editRequest);

    PageRequest pageRequest = new PageRequest(0, fileName, N + K, new Cursor(0,0));
    Page page = sourceFileHandlerLinkedListImpl.getLinesFrom(pageRequest);

    assertEquals(newContents, page.getLines());
    assertEquals(0, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  void insertLinesAtBottom() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    int N = 10;
    int K = 3;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    List<String> changedLines = new LinkedList<>();
    for (int i = 0; i < K; ++i) {
      StringBuffer buffer = new StringBuffer("LineNumber");
      buffer.append(i);
      changedLines.add(buffer.toString());
    }
    List<String> newContents = new LinkedList<>();

    newContents.addAll(fileInfo.getLines());
    newContents.addAll(changedLines);

    Cursor cursor = new Cursor(0, 0);
    EditRequest editRequest = new EditRequest( 0, N, newContents, fileName, cursor);
    sourceFileHandlerLinkedListImpl.editLines(editRequest);

    PageRequest pageRequest = new PageRequest(0, fileName, N + K, new Cursor(0,0));
    Page page = sourceFileHandlerLinkedListImpl.getLinesFrom(pageRequest);

    assertEquals(newContents, page.getLines());
    assertEquals(0, page.getStartingLineNo());
    assertEquals(cursor, page.getCursorAt());
  }

  @Test
  void randomInsertUpdateDelete() {
    int seed = 0x1231;
    Random random = new Random(seed);
    String fileName = "randomInsertUpdateDelete";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    int N = 1000;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    List<String> newContents = new LinkedList<>();
    newContents.addAll(fileInfo.getLines());

    int K = N;
    for (int i = 0; i < N; ++i) {
      int len = newContents.size();
      int toss= random.nextInt(3);
      int index = random.nextInt(len);
      if (toss < 0) {
        newContents.remove(index);
      } else if (toss < 1) {
        newContents.add(index, "Text to be inserted");
      } else {
        newContents.set(index, "Something else " + newContents.get(index) + "Something");
      }

      Cursor cursor = new Cursor(0, 0);
      EditRequest editRequest = new EditRequest( 0, K, newContents, fileName, cursor);
      sourceFileHandlerLinkedListImpl.editLines(editRequest);

      PageRequest pageRequest = new PageRequest(0, fileName, newContents.size(), new Cursor(0,0));
      Page page = sourceFileHandlerLinkedListImpl.getLinesFrom(pageRequest);

      K = page.getLines().size();

      assertEquals(newContents, page.getLines());
      assertEquals(0, page.getStartingLineNo());
      assertEquals(cursor, page.getCursorAt());
    }
  }


  @Test
  void editTotheTopOftheFileShouldBeefficientInLinkedList() {
    String fileName = "testfile";
    SourceFileHandlerLinkedListImpl sourceFileHandlerLinkedListImpl = getSourceFileHandlerLinkedList(fileName);

    int N = 10000;
    int K = 10000;
    FileInfo fileInfo = getLargeSampleFileInfo(fileName, N);
    sourceFileHandlerLinkedListImpl.loadFile(fileInfo);

    List<String> changedLines = new LinkedList<>();
    for (int i = 0; i < 1; ++i) {
      StringBuffer buffer = new StringBuffer("LINENO");
      buffer.append(i);
      changedLines.add(buffer.toString());
    }

    Cursor cursorAt = new Cursor(0, 0);
    EditRequest editRequest = new EditRequest(0, 0, changedLines, fileName, cursorAt);
    PageRequest pageRequest = new PageRequest(0, fileName, 1, cursorAt);

    long timeTaken = 0;
    for (int i = 0; i < K; ++i) {
      long startTime = System.nanoTime();
      sourceFileHandlerLinkedListImpl.editLines(editRequest);

      Page pageResponse = sourceFileHandlerLinkedListImpl.getLinesFrom(pageRequest);
      timeTaken += System.nanoTime() - startTime;
      assertEquals(changedLines, pageResponse.getLines());
    }
    System.out.println(timeTaken);
    assert(timeTaken < 330 * 1000 * 1000);
    PageRequest pageRequestNew = new PageRequest(0, fileName, N + K, cursorAt);
    Page page = sourceFileHandlerLinkedListImpl.getLinesFrom(pageRequestNew);

    assertEquals(fileInfo.getLines(), page.getLines().subList(K, K + N));
  }




}
