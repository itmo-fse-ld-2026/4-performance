package weblab.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import weblab.i18n.Messages;
import weblab.jmx.JMXConfig;
import weblab.models.CheckResult;
import weblab.models.HistoryEntry;
import weblab.models.Point;
import weblab.services.AreaCheckService;
import weblab.shapes.QuadrantShapeTemplate;
import weblab.shapes.factories.QuarterCircleFactory;
import weblab.shapes.factories.RectangleFactory;
import weblab.shapes.factories.TriangleFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSF-бин для проверки попадания точек в область на графике.
 *
 * <p>Бин является {@link RequestScoped} и обрабатывает отправку форм,
 * клики по графику и пакетную проверку точек. Использует сервис
 * {@link AreaCheckService} для проверки попадания и {@link HistoryBean}
 * для сохранения результатов.
 *
 * <p>Область проверки состоит из трёх фигур:
 * <ul>
 *   <li>Треугольник в квадранте 1 (основание = R, высота = R/2)</li>
 *   <li>Прямоугольник в квадранте 2 (R x R)</li>
 *   <li>Четверть круга в квадранте 4 (радиус = R/2)</li>
 * </ul>
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see AreaCheckService
 * @see HistoryBean
 * @see PointValidationBean
 */
@Named("pointCheckBean")
@RequestScoped
public class PointCheckBean {
    /**
     * Конструктор по умолчанию.
     */
    public PointCheckBean() {
        // Конструктор по умолчанию
    }

    /**
     * Бин для валидации координат точки.
     */
    @Inject
    private PointValidationBean validationBean;

    /**
     * Бин для сохранения истории проверок.
     */
    @Inject
    private HistoryBean historyBean;

    @Inject
    private JMXConfig jmxConfig;

    /**
     * Список точек для пакетной проверки.
     */
    private List<Point> points = new ArrayList<>();

    /**
     * Флаг, указывающий, был ли клик по графику.
     * {@code true} — точки получены из графика, {@code false} — из формы.
     */
    private boolean graphClick = false;

    /**
     * Сервис проверки попадания точек в область.
     * Содержит конфигурацию фигур по квадрантам.
     */
    private final AreaCheckService areaCheckService = new AreaCheckService(List.of(
            new QuadrantShapeTemplate(new TriangleFactory(1.0, 0.5), 1),
            new QuadrantShapeTemplate(new RectangleFactory(1.0, 1.0), 2),
            new QuadrantShapeTemplate(new QuarterCircleFactory(0.5), 4)
    ));

    /**
     * Выбранное значение X из формы. Хранится как строка для возможности ввода дробных чисел.
     */
    private String selectedX;

    /**
     * Выбранное значение Y из формы.
     */
    private Double selectedY;

    /**
     * Массив выбранных значений R (checkboxes).
     * Индексы соответствуют значениям: 0→1.0, 1→1.5, 2→2.0, 3→2.5, 4→3.0
     */
    private boolean[] rValues = new boolean[5];

    /**
     * Координата X, полученная из графика при клике.
     */
    private Double graphX;

    /**
     * Координата Y, полученная из графика при клике.
     */
    private Double graphY;

    /**
     * Значение R, полученное из графика.
     */
    private Double graphR;

    // ==================== Getters and Setters ====================

    /**
     * Возвращает список точек для пакетной проверки.
     *
     * @return список точек
     */
    public List<Point> getPoints() { return points; }

    /**
     * Устанавливает список точек для пакетной проверки.
     *
     * @param points список точек
     */
    public void setPoints(List<Point> points) { this.points = points; }

    /**
     * Возвращает флаг, указывающий, был ли клик по графику.
     *
     * @return {@code true} если точки из графика, иначе {@code false}
     */
    public boolean isGraphClick() { return graphClick; }

    /**
     * Устанавливает флаг клика по графику.
     *
     * @param graphClick {@code true} если точки из графика
     */
    public void setGraphClick(boolean graphClick) { this.graphClick = graphClick; }

    /**
     * Возвращает выбранное значение X.
     *
     * @return значение X в виде строки
     */
    public String getSelectedX() { return selectedX; }

    /**
     * Устанавливает выбранное значение X.
     *
     * @param selectedX значение X в виде строки
     */
    public void setSelectedX(String selectedX) { this.selectedX = selectedX; }

    /**
     * Возвращает выбранное значение Y.
     *
     * @return значение Y
     */
    public Double getSelectedY() { return selectedY; }

    /**
     * Устанавливает выбранное значение Y.
     *
     * @param selectedY значение Y
     */
    public void setSelectedY(Double selectedY) { this.selectedY = selectedY; }

    /**
     * Возвращает массив выбранных значений R.
     *
     * @return массив boolean для каждого R
     */
    public boolean[] getRValues() { return rValues; }

    /**
     * Устанавливает массив выбранных значений R.
     *
     * @param rValues массив boolean для каждого R
     */
    public void setRValues(boolean[] rValues) { this.rValues = rValues; }

    // Именованные свойства для JSF

    /**
     * Возвращает выбран ли R=1.0.
     *
     * @return {@code true} если R=1.0 выбран, иначе {@code false}
     */
    public boolean getZero() { return rValues[0]; }

    /**
     * Устанавливает выбор R=1.0.
     *
     * @param value {@code true} если R=1.0 выбран, иначе {@code false}
     */
    public void setZero(boolean value) { rValues[0] = value; }

    /**
     * Возвращает выбран ли R=1.5.
     *
     * @return {@code true} если R=1.5 выбран, иначе {@code false}
     */
    public boolean getOne() { return rValues[1]; }

    /**
     * Устанавливает выбор R=1.5.
     *
     * @param value {@code true} если R=1.5 выбран, иначе {@code false}
     */
    public void setOne(boolean value) { rValues[1] = value; }

    /**
     * Возвращает выбран ли R=2.0.
     *
     * @return {@code true} если R=2.0 выбран, иначе {@code false}
     */
    public boolean getTwo() { return rValues[2]; }

    /**
     * Устанавливает выбор R=2.0.
     *
     * @param value {@code true} если R=2.0 выбран, иначе {@code false}
     */
    public void setTwo(boolean value) { rValues[2] = value; }

    /**
     * Возвращает выбран ли R=2.5.
     *
     * @return {@code true} если R=2.5 выбран, иначе {@code false}
     */
    public boolean getThree() { return rValues[3]; }

    /**
     * Устанавливает выбор R=2.5.
     *
     * @param value {@code true} если R=2.5 выбран, иначе {@code false}
     */
    public void setThree(boolean value) { rValues[3] = value; }

    /**
     * Возвращает выбран ли R=3.0.
     *
     * @return {@code true} если R=3.0 выбран, иначе {@code false}
     */
    public boolean getFour() { return rValues[4]; }

    /**
     * Устанавливает выбор R=3.0.
     *
     * @param value {@code true} если R=3.0 выбран, иначе {@code false}
     */
    public void setFour(boolean value) { rValues[4] = value; }

    /**
     * Возвращает координату X из графика.
     *
     * @return координата X
     */
    public Double getGraphX() { return graphX; }

    /**
     * Устанавливает координату X из графика.
     *
     * @param graphX координата X
     */
    public void setGraphX(Double graphX) { this.graphX = graphX; }

    /**
     * Возвращает координату Y из графика.
     *
     * @return координата Y
     */
    public Double getGraphY() { return graphY; }

    /**
     * Устанавливает координату Y из графика.
     *
     * @param graphY координата Y
     */
    public void setGraphY(Double graphY) { this.graphY = graphY; }

    /**
     * Возвращает значение R из графика.
     *
     * @return значение R
     */
    public Double getGraphR() { return graphR; }

    /**
     * Устанавливает значение R из графика.
     *
     * @param graphR значение R
     */
    public void setGraphR(Double graphR) { this.graphR = graphR; }

    // ==================== Business Methods ====================

    /**
     * Обрабатывает отправку формы из графика (клик по графику).
     *
     * <p>Получает координаты X, Y и R из компонента графика,
     * нормализует их (X и Y ограничиваются диапазоном [-6, 6],
     * R округляется до ближайшего допустимого значения),
     * выполняет проверку попадания и сохраняет результат.
     *
     * <p>В случае ошибки валидации или некорректного значения R
     * в JSF-контекст добавляется сообщение об ошибке.
     */
    public void submitFromGraph() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (graphX == null || graphY == null || graphR == null) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("error.graph.coords"), null));
            return;
        }

        String sessionID = context.getExternalContext().getSessionId(false);
        if (sessionID == null) {
            sessionID = context.getExternalContext().getSessionId(true);
        }

        double x = Math.max(-6.0, Math.min(6.0, graphX));
        double y = Math.max(-6.0, Math.min(6.0, graphY));

        double[] validR = {1.0, 1.5, 2.0, 2.5, 3.0};
        boolean validRValue = false;
        double rValue = graphR;
        for (double r : validR) {
            if (Math.abs(graphR - r) < 1e-6) {
                validRValue = true;
                rValue = r;
                break;
            }
        }

        if (!validRValue) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    Messages.format("error.invalid.r", graphR), null));
            return;
        }

        Point point = new Point(x, y, rValue);

        LocalDateTime timestamp = LocalDateTime.now();
        long start = System.nanoTime();

        List<CheckResult> results = areaCheckService.checkPoints(List.of(point));

        double execTime = (System.nanoTime() - start) / 1e6;

        boolean hit = results.get(0).hit();
        jmxConfig.getPointCounter().addPointEvent(hit);
        jmxConfig.getMissPercentage().addClickEvent(hit);

        String finalSessionID = sessionID;
        List<HistoryEntry> entries = results.stream()
                .map(res -> new HistoryEntry(res.point(), res.hit(), timestamp, execTime, finalSessionID))
                .toList();

        historyBean.saveBatch(entries);
    }

    /**
     * Обрабатывает отправку формы с заданными значениями координат.
     *
     * <p>Метод вызывается из JSF-формы. Выполняет валидацию входных данных:
     * <ul>
     *   <li>Проверяет, что X введён и является числом</li>
     *   <li>Проверяет, что выбрано хотя бы одно значение R</li>
     *   <li>Валидирует все точки через {@link PointValidationBean#validateForm(Point)}</li>
     * </ul>
     *
     * <p>Для каждого выбранного значения R создаётся точка с одинаковыми X и Y,
     * выполняется проверка попадания и сохраняются результаты.
     *
     * @param y значение координаты Y (целое число от -3 до 5)
     */
    public void submitForm(int y) {
        FacesContext context = FacesContext.getCurrentInstance();
        String sessionID = context.getExternalContext().getSessionId(false);
        if (sessionID == null) {
            sessionID = context.getExternalContext().getSessionId(true);
        }

        double[] rMappings = {1.0, 1.5, 2.0, 2.5, 3.0};
        List<Double> selectedRValues = new ArrayList<>();
        for (int i = 0; i < rValues.length && i < rMappings.length; i++) {
            if (rValues[i]) selectedRValues.add(rMappings[i]);
        }

        if (selectedX == null || selectedX.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("error.enter.x"), null));
            return;
        }

        if (selectedRValues.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("error.select.r"), null));
            return;
        }

        double x;
        try {
            x = Double.parseDouble(selectedX.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("error.invalid.x"), null));
            return;
        }

        double yValue = (double) y;

        List<Point> pointsToCheck = selectedRValues.stream()
                .map(r -> new Point(x, yValue, r))
                .toList();

        List<Point> invalidPoints = pointsToCheck.stream()
                .filter(p -> !validationBean.validateForm(p))
                .toList();

        if (!invalidPoints.isEmpty()) {
            String message = formatInvalidPoints(invalidPoints);
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            Messages.format("error.invalid.points", message), null));
            return;
        }

        LocalDateTime timestamp = LocalDateTime.now();
        long start = System.nanoTime();

        List<CheckResult> results = areaCheckService.checkPoints(pointsToCheck);

        double execTime = (System.nanoTime() - start) / 1e6;

        for (CheckResult result : results) {
            jmxConfig.getPointCounter().addPointEvent(result.hit());
        }

        String finalSessionID = sessionID;
        List<HistoryEntry> entries = results.stream()
                .map(r -> new HistoryEntry(r.point(), r.hit(), timestamp, execTime, finalSessionID))
                .toList();

        historyBean.saveBatch(entries);
    }

    /**
     * Обрабатывает пакетную проверку точек.
     *
     * <p>Проверяет список точек, накопленных в поле {@code points}.
     * В зависимости от флага {@code graphClick} использует соответствующую
     * валидацию: для клика по графику проверяются границы видимой области,
     * для формы — стандартные границы.
     *
     * <p>Если есть некорректные точки, в JSF-контекст добавляется сообщение
     * об ошибке со списком проблемных точек.
     *
     * @param graphXMin минимальная граница X для клика по графику
     * @param graphXMax максимальная граница X для клика по графику
     * @param graphYMin минимальная граница Y для клика по графику
     * @param graphYMax максимальная граница Y для клика по графику
     * @param sessionID идентификатор HTTP-сессии
     */
    public void submitPoints(double graphXMin, double graphXMax, double graphYMin, double graphYMax, String sessionID) {
        FacesContext context = FacesContext.getCurrentInstance();

        if (points == null || points.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, Messages.get("error.no.points"), null));
            return;
        }

        List<Point> invalidPoints = points.stream()
                .filter(p -> graphClick
                        ? !validationBean.validateGraphClick(p, graphXMin, graphXMax, graphYMin, graphYMax)
                        : !validationBean.validateForm(p))
                .toList();

        if (!invalidPoints.isEmpty()) {
            String message = formatInvalidPoints(invalidPoints);
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            Messages.format("error.invalid.points", message), null));
            return;
        }

        LocalDateTime timestamp = LocalDateTime.now();
        long start = System.nanoTime();

        List<CheckResult> results = areaCheckService.checkPoints(points);

        double execTime = (System.nanoTime() - start) / 1e6;

        for (CheckResult result : results) {
            jmxConfig.getPointCounter().addPointEvent(result.hit());
        }

        List<HistoryEntry> entries = results.stream()
                .map(r -> new HistoryEntry(r.point(), r.hit(), timestamp, execTime, sessionID))
                .toList();

        historyBean.saveBatch(entries);
    }

    /**
     * Форматирует список некорректных точек для отображения в сообщении об ошибке.
     *
     * @param invalidPoints список некорректных точек
     * @return строка с форматированными координатами точек
     */
    private static String formatInvalidPoints(List<Point> invalidPoints) {
        return invalidPoints.stream()
                .map(p -> Messages.format("point.format", p.x(), p.y(), p.r()))
                .collect(Collectors.joining("; "));
    }
}