package br.com.monitodehabitos.monitodehabitos.domain.entities;


import br.com.monitodehabitos.monitodehabitos.domain.exception.HabitExeption;
import br.com.monitodehabitos.monitodehabitos.domain.factories.FactoryHabit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

public class ProgressTest {

    @Test
    @DisplayName("should be change the status")
    void scneario01() throws HabitExeption {
        Client client = Mockito.mock(Client.class);
        FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(
                client,
                "Descrição genérica de algo",
                HabitStatus.NOT_STARTED,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 5)
        );
        Assertions.assertEquals(ProgressEnumStatus.NOT_STARTED, habit.getWeeks().getFirst().getProgresses().getFirst().getProgressEnumStatus());
        habit.getWeeks().getFirst().getProgresses().getFirst().changeStatusToCompleteOrNot();
        Assertions.assertEquals(ProgressEnumStatus.COMPLETED, habit.getWeeks().getFirst().getProgresses().getFirst().getProgressEnumStatus());
    }
}
