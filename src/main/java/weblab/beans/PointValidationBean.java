package weblab.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import weblab.models.Point;

import java.util.Arrays;

/**
 * Бин для валидации входных данных точек.
 *
 * <p>Обеспечивает проверку корректности координат точки в зависимости
 * от способа ввода (через форму или клик по графику). Бин является
 * {@link RequestScoped} и создаётся для каждого HTTP-запроса.
 *
 * <p>Допустимые значения:
 * <ul>
 *   <li>X: от -3 до 5 (любое double в этом диапазоне)</li>
 *   <li>Y: только целые значения {-3, -2, -1, 0, 1, 2, 3, 4, 5}</li>
 *   <li>R: только значения {1, 1.5, 2, 2.5, 3}</li>
 * </ul>
 *
 *
 * <p>Для клика по графику границы X и Y могут быть другими
 * (например, от -6 до 6), что соответствует видимой области графика.
 *
 * @author Vladislav Dyadev
 * @version 1.0
 * @see Point
 */
@Named("pointValidationBean")
@RequestScoped
public class PointValidationBean {
    /** Допустимые значения радиуса R. */
    private static final double[] R_VALUES = {1, 1.5, 2, 2.5, 3};

    /** Допустимые значения координаты Y (только целые). */
    private static final int[] Y_VALUES = {-3, -2, -1, 0, 1, 2, 3, 4, 5};

    /** Минимальное допустимое значение X. */
    private static final double X_MIN = -3;

    /** Максимальное допустимое значение X. */
    private static final double X_MAX = 5;

    /** Погрешность для сравнения чисел с плавающей точкой. */
    private static final double EPSILON = 1e-6;

    /**
     * Проверяет корректность точки, введённой через форму.
     *
     * <p>Валидация выполняется по следующим правилам:
     * <ul>
     *   <li>X должен быть в диапазоне [-3, 5] (с учётом погрешности)</li>
     *   <li>Y должен быть одним из целых значений: -3, -2, -1, 0, 1, 2, 3, 4, 5</li>
     *   <li>R должен быть одним из значений: 1, 1.5, 2, 2.5, 3</li>
     * </ul>
     *
     *
     * @param point проверяемая точка (не может быть {@code null})
     * @return {@code true}, если точка соответствует всем критериям валидации,
     *         {@code false} в противном случае
     */
    public boolean validateForm(Point point) {
        if (point == null) return false;

        boolean validX = point.x() >= X_MIN - EPSILON && point.x() <= X_MAX + EPSILON;

        boolean validY = Arrays.stream(Y_VALUES)
                .anyMatch(y -> Math.abs(point.y() - y) <= EPSILON);

        boolean validR = Arrays.stream(R_VALUES)
                .anyMatch(r -> Math.abs(point.r() - r) <= EPSILON);

        return validX && validY && validR;
    }

    /**
     * Проверяет корректность точки, введённой через клик по графику.
     *
     * <p>В отличие от {@link #validateForm(Point)}, этот метод использует
     * динамические границы для X и Y, которые передаются как параметры.
     * Это позволяет валидировать точки в пределах видимой области графика
     * (обычно от -6 до 6 по обеим осям).
     *
     * <p>Правила валидации R остаются теми же: допустимые значения
     * {1, 1.5, 2, 2.5, 3}.
     *
     * @param point       проверяемая точка (не может быть {@code null})
     * @param graphXMin   минимальное допустимое значение X для графика
     * @param graphXMax   максимальное допустимое значение X для графика
     * @param graphYMin   минимальное допустимое значение Y для графика
     * @param graphYMax   максимальное допустимое значение Y для графика
     * @return {@code true}, если координаты точки находятся в указанных границах
     *         и R соответствует допустимым значениям, {@code false} в противном случае
     */
    public boolean validateGraphClick(Point point, double graphXMin, double graphXMax, double graphYMin, double graphYMax) {
        if (point == null) return false;

        boolean validX = point.x() >= graphXMin - EPSILON && point.x() <= graphXMax + EPSILON;

        boolean validY = point.y() >= graphYMin - EPSILON && point.y() <= graphYMax + EPSILON;

        boolean validR = Arrays.stream(R_VALUES)
                .anyMatch(r -> Math.abs(point.r() - r) <= EPSILON);

        return validX && validY && validR;
    }
}