package weblab.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import weblab.db.DBUtil;
import weblab.db.SQLQueries;
import weblab.models.HistoryEntry;
import weblab.models.Point;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Тесты работы с БД")
public class ResultDAOTest {
    
    private static MockedStatic<weblab.i18n.Messages> messagesMock;

    @BeforeAll
    static void setupSuite() {
        System.setProperty("java.naming.factory.initial", DummyContextFactory.class.getName());
        
        messagesMock = mockStatic(weblab.i18n.Messages.class);
        messagesMock.when(() -> weblab.i18n.Messages.get(org.mockito.ArgumentMatchers.anyString()))
                    .thenReturn("dummy_db_resource");
    }

    @AfterAll
    static void tearDownSuite() {
        if (messagesMock != null) {
            messagesMock.close();
        }
    }

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private ResultDAO resultDAO;
    private MockedStatic<DBUtil> dbUtilMock;

    @BeforeEach
    void setUp() {
        resultDAO = new ResultDAO();
        dbUtilMock = mockStatic(DBUtil.class);
        dbUtilMock.when(DBUtil::getConnection).thenReturn(mockConnection);
    }

    @AfterEach
    void tearDown() {
        if (dbUtilMock != null) {
            dbUtilMock.close();
        }
    }

    private void setupMockStatement(String sql) throws SQLException {
        when(mockConnection.prepareStatement(sql)).thenReturn(mockPreparedStatement);
    }

    @Test
    @DisplayName("Сохранение пакета результатов - должен выполнить batch insert")
    void saveResultsBatchShouldExecuteBatchInsert() throws SQLException {
        setupMockStatement(SQLQueries.INSERT_RESULT);

        List<HistoryEntry> entries = List.of(
                new HistoryEntry(new Point(1.0, 2.0, 3.0), true, LocalDateTime.now(), 0.5, "session1"),
                new HistoryEntry(new Point(4.0, 5.0, 6.0), false, LocalDateTime.now(), 0.3, "session2")
        );

        resultDAO.saveResultsBatch(entries);

        verify(mockPreparedStatement, times(2)).addBatch();
        verify(mockPreparedStatement).executeBatch();
        verify(mockConnection).commit();
        verify(mockConnection).setAutoCommit(false);
    }

    @Test
    @DisplayName("Сохранение пустого списка - addBatch не вызывается, executeBatch вызывается")
    void saveEmptyListShouldNotExecuteAddBatchButExecuteBatchIsCalled() throws SQLException {
        setupMockStatement(SQLQueries.INSERT_RESULT);

        resultDAO.saveResultsBatch(List.of());

        verify(mockPreparedStatement, never()).addBatch();
        verify(mockPreparedStatement).executeBatch();
        verify(mockConnection).commit();
        verify(mockConnection).setAutoCommit(false);
    }

    @Test
    @DisplayName("Сохранение одной записи - должен выполнить один addBatch")
    void saveSingleEntryShouldExecuteOneAddBatch() throws SQLException {
        setupMockStatement(SQLQueries.INSERT_RESULT);

        List<HistoryEntry> entries = List.of(
                new HistoryEntry(new Point(1.0, 2.0, 3.0), true, LocalDateTime.now(), 0.5, "session1")
        );

        resultDAO.saveResultsBatch(entries);

        verify(mockPreparedStatement, times(1)).addBatch();
        verify(mockPreparedStatement).executeBatch();
        verify(mockConnection).commit();
        verify(mockConnection).setAutoCommit(false);
    }

    @Test
    @DisplayName("Получение всех результатов - должен корректно маппить ResultSet")
    void getAllResultsShouldMapResultSetCorrectly() throws SQLException {
        setupMockStatement(SQLQueries.SELECT_ALL_RESULTS);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getDouble("x")).thenReturn(1.0, 4.0);
        when(mockResultSet.getDouble("y")).thenReturn(2.0, 5.0);
        when(mockResultSet.getDouble("r")).thenReturn(3.0, 6.0);
        when(mockResultSet.getBoolean("hit")).thenReturn(true, false);
        when(mockResultSet.getString("session_id")).thenReturn("session1", "session2");
        when(mockResultSet.getTimestamp("timestamp")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getDouble("exec_time")).thenReturn(0.5, 0.3);

        List<HistoryEntry> results = resultDAO.getAllResults();

        assertEquals(2, results.size());
        assertEquals(1.0, results.get(0).point().x());
        assertEquals(4.0, results.get(1).point().x());
        assertTrue(results.get(0).result());
        assertFalse(results.get(1).result());
    }

    @Test
    @DisplayName("Получение всех результатов - пустой ResultSet")
    void getAllResultsEmptyShouldReturnEmptyList() throws SQLException {
        setupMockStatement(SQLQueries.SELECT_ALL_RESULTS);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        List<HistoryEntry> results = resultDAO.getAllResults();

        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Получение по сессии - параметризованный запрос")
    void getResultsBySessionShouldUseParameterizedQuery() throws SQLException {
        setupMockStatement(SQLQueries.SELECT_RESULTS_BY_SESSION);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        resultDAO.getResultsBySession("test-session");

        verify(mockPreparedStatement).setString(1, "test-session");
        verify(mockPreparedStatement).executeQuery();
    }

    @Test
    @DisplayName("Получение по сессии - корректный маппинг")
    void getResultsBySessionShouldMapCorrectly() throws SQLException {
        setupMockStatement(SQLQueries.SELECT_RESULTS_BY_SESSION);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true, false);
        when(mockResultSet.getDouble("x")).thenReturn(1.0);
        when(mockResultSet.getDouble("y")).thenReturn(2.0);
        when(mockResultSet.getDouble("r")).thenReturn(3.0);
        when(mockResultSet.getBoolean("hit")).thenReturn(true);
        when(mockResultSet.getString("session_id")).thenReturn("test-session");
        when(mockResultSet.getTimestamp("timestamp")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getDouble("exec_time")).thenReturn(0.5);

        List<HistoryEntry> results = resultDAO.getResultsBySession("test-session");

        assertEquals(1, results.size());
        assertEquals("test-session", results.get(0).sessionId());
    }

    @Test
    @DisplayName("Получение по сессии - сессия не найдена")
    void getResultsBySessionNotFoundShouldReturnEmptyList() throws SQLException {
        setupMockStatement(SQLQueries.SELECT_RESULTS_BY_SESSION);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        List<HistoryEntry> results = resultDAO.getResultsBySession("non-existent");

        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Удаление по сессии - DELETE с параметром")
    void removeResultsBySessionShouldExecuteParameterizedDelete() throws SQLException {
        setupMockStatement(SQLQueries.DELETE_RESULTS_BY_SESSION);
        when(mockPreparedStatement.executeUpdate()).thenReturn(5);

        resultDAO.removeResultsBySession("test-session");

        verify(mockPreparedStatement).setString(1, "test-session");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Удаление по сессии - сессия не найдена")
    void removeResultsBySessionNotFoundDeletesZeroRows() throws SQLException {
        setupMockStatement(SQLQueries.DELETE_RESULTS_BY_SESSION);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        resultDAO.removeResultsBySession("non-existent");

        verify(mockPreparedStatement).setString(1, "non-existent");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Очистка всех результатов - DELETE без условий")
    void clearResultsShouldExecuteDeleteWithoutConditions() throws SQLException {
        setupMockStatement(SQLQueries.DELETE_ALL_RESULTS);
        when(mockPreparedStatement.executeUpdate()).thenReturn(10);

        resultDAO.clearResults();

        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    @DisplayName("Очистка пустой таблицы")
    void clearResultsEmptyTableShouldReturnZero() throws SQLException {
        setupMockStatement(SQLQueries.DELETE_ALL_RESULTS);
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        resultDAO.clearResults();

        verify(mockPreparedStatement).executeUpdate();
    }

    public static class DummyContextFactory implements javax.naming.spi.InitialContextFactory {
        @Override
        public javax.naming.Context getInitialContext(java.util.Hashtable<?, ?> environment) throws javax.naming.NamingException {
            javax.naming.Context mockContext = mock(javax.naming.Context.class);
            try {
                javax.sql.DataSource mockDS = mock(javax.sql.DataSource.class);
                java.sql.Connection mockConn = mock(java.sql.Connection.class);
                java.sql.Statement mockStmt = mock(java.sql.Statement.class);
                java.sql.PreparedStatement mockPrepStmt = mock(java.sql.PreparedStatement.class);
                java.sql.ResultSet mockRS = mock(java.sql.ResultSet.class);

                org.mockito.Mockito.lenient().when(mockConn.createStatement()).thenReturn(mockStmt);
                org.mockito.Mockito.lenient().when(mockConn.prepareStatement(org.mockito.ArgumentMatchers.anyString())).thenReturn(mockPrepStmt);
                org.mockito.Mockito.lenient().when(mockStmt.executeQuery(org.mockito.ArgumentMatchers.anyString())).thenReturn(mockRS);
                org.mockito.Mockito.lenient().when(mockDS.getConnection()).thenReturn(mockConn);
                
                org.mockito.Mockito.lenient().when(mockContext.lookup(org.mockito.ArgumentMatchers.anyString())).thenReturn(mockDS);
            } catch (Exception ignored) {
            }
            return mockContext;
        }
    }
}