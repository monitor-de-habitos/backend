package br.com.monitodehabitos.monitodehabitos.domain.entities;

import br.com.monitodehabitos.monitodehabitos.domain.exception.HabitExeption;
import br.com.monitodehabitos.monitodehabitos.domain.factories.FactoryHabit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.UUID;

public class HabitTest {

    private String id = UUID.randomUUID().toString();

    @Test
    @DisplayName("Should be to change the the status 'done'")
    void scenario01() throws HabitExeption {
        Client client = Mockito.mock(Client.class);
        FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(client, "Descrição genéria de algo", HabitStatus.NOT_STARTED, LocalDate.now(), LocalDate.now());
        habit.changeStatus(HabitStatus.IN_PROGRESS);
        Assertions.assertEquals(HabitStatus.IN_PROGRESS, habit.getDone());
    }

    @Test
    @DisplayName("Should be change habit status")
    void scenario02() throws HabitExeption {
        Client client = Mockito.mock(Client.class);
        FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(client, "Descrição genéria de algo", HabitStatus.NOT_STARTED, LocalDate.now(), LocalDate.now());
        Habit newHabit = new Habit(
                null,
                "Nova descrição",
                null,
                null,
                null,
                null,
                null
        );
        habit.update(newHabit);
        Assertions.assertEquals(habit.getDescription(), newHabit.getDescription());
    }

    @Test
    @DisplayName("Should return error when updating habit with null")
    void scenario03() throws HabitExeption {
        // Arrange
        Client client = Mockito.mock(Client.class);
        FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(
                client,
                "Descrição genérica de algo",
                HabitStatus.NOT_STARTED,
                LocalDate.now(),
                LocalDate.now()
        );
        // Act & Assert
        HabitExeption exception = Assertions.assertThrows(
                HabitExeption.class,
                () -> habit.update(null),
                "Expected HabitExeption when updating with null"
        );

        Assertions.assertEquals("Erro ao atualizar o hábito", exception.getMessage());
    }

    @Test
    @DisplayName("Should verify the creation of habit weeks")
    void scenario04() throws HabitExeption {
        Client client = Mockito.mock(Client.class);
        FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(
                client,
                "Descrição genérica de algo",
                HabitStatus.NOT_STARTED,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 31)
        );
        System.out.println(habit.getWeeks().getFirst().toString());
        Assertions.assertEquals(5, habit.getWeeks().size());
        Assertions.assertEquals(LocalDate.of(2024, 12, 1), habit.getWeeks().getFirst().getStartDate());
        Assertions.assertEquals(LocalDate.of(2024, 12, 7), habit.getWeeks().getFirst().getEndDate());
        Assertions.assertEquals(14.29, habit.getWeeks().getFirst().getPercentagePerDay());
        Assertions.assertEquals(0, habit.getWeeks().getFirst().getTotalPercentage());
    }

    @Test
    @DisplayName("Should verify the creation of habit weeks")
    void scenario05() throws HabitExeption {
        Client client = Mockito.mock(Client.class);
        FactoryHabit factoryHabit = new FactoryHabit();
        Habit habit = factoryHabit.withDescriptionAndDate(
                client,
                "Descrição genérica de algo",
                HabitStatus.NOT_STARTED,
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 31)
        );
        Assertions.assertEquals(5, habit.getWeeks().size());
        Assertions.assertEquals(LocalDate.of(2024, 12, 1), habit.getWeeks().getFirst().getStartDate());
        Assertions.assertEquals(LocalDate.of(2024, 12, 7), habit.getWeeks().getFirst().getEndDate());
        Assertions.assertEquals(14.29, habit.getWeeks().getFirst().getPercentagePerDay());
        Assertions.assertEquals(0, habit.getWeeks().getFirst().getTotalPercentage());
    }

}
