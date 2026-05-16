package weblab.db;

import weblab.i18n.Messages;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Утилитарный класс для работы с подключением к базе данных.
 *
 * <p>Обеспечивает:
 * <ul>
 *   <li>Получение DataSource через JNDI lookup</li>
 *   <li>Создание таблицы результатов при первом обращении</li>
 *   <li>Предоставление подключений к базе данных</li>
 * </ul>
 * </p>
 *
 * <p>DataSource ищется по JNDI имени {@code java:/jdbc/web3DB},
 * что предполагает настройку ресурса в контейнере сервлетов (Tomcat, JBoss и т.д.).</p>
 *
 * <p>При инициализации класса автоматически создаётся таблица {@code results},
 * если она ещё не существует.</p>
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see SQLQueries
 * @see javax.sql.DataSource
 */
public class DBUtil {
    /**
     * DataSource для подключения к базе данных.
     * Инициализируется в статическом блоке при загрузке класса.
     */
    public static DataSource ds;

    static {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:/jdbc/web3DB");

            createTableIfNotExists();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает соединение с базой данных.
     *
     * <p>Если DataSource не был инициализирован (например, при ошибке JNDI lookup),
     * выбрасывается исключение.</p>
     *
     * @return соединение с базой данных
     * @throws SQLException если DataSource не инициализирован
     *         или произошла ошибка при получении соединения
     */
    public static Connection getConnection() throws SQLException {
        if (ds == null) throw new SQLException(Messages.get("error.datasource.not.initialized"));
        return ds.getConnection();
    }

    /**
     * Создаёт таблицу {@code results}, если она не существует.
     *
     * <p>SQL-запрос для создания таблицы определён в {@link SQLQueries#CREATE_TABLE_RESULTS}.
     * Метод вызывается при загрузке класса и не должен использоваться повторно.</p>
     *
     * @throws SQLException если произошла ошибка при выполнении SQL-запроса
     */
    private static void createTableIfNotExists() throws SQLException {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(SQLQueries.CREATE_TABLE_RESULTS);
        }
    }
}