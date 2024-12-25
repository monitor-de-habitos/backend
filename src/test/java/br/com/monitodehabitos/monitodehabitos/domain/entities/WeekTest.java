package br.com.monitodehabitos.monitodehabitos.domain.entities;

import br.com.monitodehabitos.monitodehabitos.domain.exception.HabitExeption;
import br.com.monitodehabitos.monitodehabitos.domain.exception.WeekException;
import br.com.monitodehabitos.monitodehabitos.domain.factories.FactoryHabit;
import br.com.monitodehabitos.monitodehabitos.domain.factories.FactoryWeek;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

class WeekTest {

    @Test
    @DisplayName("Should be add, total percentage")
    void addPercentage() throws HabitExeption, WeekException {
       Client client = Mockito.mock(Client.class);
       FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(
                client,
                "Descrição genérica de algo",
                HabitStatus.NOT_STARTED,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 31)
        );

        habit.getWeeks().getFirst().addPercentage(habit.getWeeks().getFirst().getPercentagePerDay());
        Assertions.assertEquals(14.29, habit.getWeeks().getFirst().getTotalPercentage());
    }

    @Test
    @DisplayName("Should be until 100% ")
    void scenario02() throws HabitExeption, WeekException {
        Client client = Mockito.mock(Client.class);
        FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(
                client,
                "Descrição genérica de algo",
                HabitStatus.NOT_STARTED,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 31)
        );

        for (int i = 0; i < 7; i++){
            habit.getWeeks().getFirst().addPercentage(habit.getWeeks().getFirst().getPercentagePerDay());
        }
        Assertions.assertEquals(100, habit.getWeeks().getFirst().getTotalPercentage());
    }

    @Test
    @DisplayName("Should be until 100% if the add exceed 100 and be smaller than it plus 100 ")
    void scenario03() throws HabitExeption, WeekException {
        Client client = Mockito.mock(Client.class);
        FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(
                client,
                "Descrição genérica de algo",
                HabitStatus.NOT_STARTED,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 31)
        );

        for (int i = 0; i < 8; i++){
            habit.getWeeks().getFirst().addPercentage(habit.getWeeks().getFirst().getPercentagePerDay());
        }
        Assertions.assertEquals(100, habit.getWeeks().getFirst().getTotalPercentage());
    }

    @Test
    @DisplayName("Should be remove, 50% ")
    void scenario4() throws HabitExeption, WeekException {

        Client client = Mockito.mock(Client.class);
        FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(
                client,
                "Descrição genérica de algo",
                HabitStatus.NOT_STARTED,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 2)
        );

        for (int i = 0; i < 2; i++){
            habit.getWeeks().getFirst().addPercentage(habit.getWeeks().getFirst().getPercentagePerDay());
        }
        habit.getWeeks().getFirst().subtractPercentage(habit.getWeeks().getFirst().getPercentagePerDay());
        Assertions.assertEquals(50, habit.getWeeks().getFirst().getTotalPercentage());
    }


 @Test
    @DisplayName("Should be return 0 ")
    void scenario5() throws HabitExeption, WeekException {

     Client client = Mockito.mock(Client.class);
     FactoryHabit factoryHabit = new FactoryHabit();
     Habit habit = factoryHabit.withDescriptionAndDate(
             client,
             "Descrição genérica de algo",
             HabitStatus.NOT_STARTED,
             LocalDate.of(2024, 12, 1),
             LocalDate.of(2024, 12, 2)
     );


     habit.getWeeks().getFirst().addPercentage(habit.getWeeks().getFirst().getPercentagePerDay());

     habit.getWeeks().getFirst().subtractPercentage(habit.getWeeks().getFirst().getPercentagePerDay());
     habit.getWeeks().getFirst().subtractPercentage(habit.getWeeks().getFirst().getPercentagePerDay());
     Assertions.assertEquals(0, habit.getWeeks().getFirst().getTotalPercentage());
    }


}