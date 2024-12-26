package br.com.monitodehabitos.monitodehabitos.infra.controller;

import br.com.monitodehabitos.monitodehabitos.application.useCases.Client.FindClient;
import br.com.monitodehabitos.monitodehabitos.application.useCases.Habit.*;
import br.com.monitodehabitos.monitodehabitos.domain.entities.Client;
import br.com.monitodehabitos.monitodehabitos.domain.entities.Habit;
import br.com.monitodehabitos.monitodehabitos.domain.exception.HabitExeption;
import br.com.monitodehabitos.monitodehabitos.domain.exception.WeekException;
import br.com.monitodehabitos.monitodehabitos.domain.factories.FactoryHabit;
import br.com.monitodehabitos.monitodehabitos.infra.controller.habitDto.request.ChangeDoneDto;
import br.com.monitodehabitos.monitodehabitos.infra.controller.habitDto.request.CreateHabitDto;
import br.com.monitodehabitos.monitodehabitos.infra.controller.habitDto.request.UpdateHabitDto;
import br.com.monitodehabitos.monitodehabitos.infra.controller.habitDto.response.ResponseHabitDto;
import br.com.monitodehabitos.monitodehabitos.infra.utils.DateValidationAndFormater;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import static br.com.monitodehabitos.monitodehabitos.infra.utils.ApplicationLogs.log;
@RestController
@RequestMapping("/habit")
public class HabitController {
    private final FactoryHabit factoryHabit;
    private final CreateHabit createHabit;
    private final FindClient findClient;
    private final FindHabit findHabit;
    private final DeleteHabit deleteHabit;
    private final FindAllByUser findAllByUser;
    private final UpdateHabit updateHabit;
    private final ChangeDoHabit changeDoHabit;
    private final DateValidationAndFormater dateValidationAndFormater;


    public HabitController(FactoryHabit factoryHabit, CreateHabit createHabit, FindClient findClient, FindHabit findHabit, DeleteHabit deleteHabit, FindAllByUser findAllByUser, UpdateHabit updateHabit, ChangeDoHabit changeDoHabit, DateValidationAndFormater dateValidationAndFormater) {
        this.factoryHabit = factoryHabit;
        this.createHabit = createHabit;
        this.findClient = findClient;
        this.findHabit = findHabit;
        this.deleteHabit = deleteHabit;
        this.findAllByUser = findAllByUser;
        this.updateHabit = updateHabit;
        this.changeDoHabit = changeDoHabit;
        this.dateValidationAndFormater = dateValidationAndFormater;
    }

    @PostMapping
    public ResponseEntity<ResponseHabitDto> create(@RequestBody @Valid CreateHabitDto data) throws HabitExeption, WeekException {
        log.info("Início da criação do hábito::HabitController");
        Client client = this.findClient.findClient(data.clientId());
        var dateStart = this.dateValidationAndFormater.validate(data.start());
        var dateEnd = this.dateValidationAndFormater.validate(data.end());
        Habit habit = this.factoryHabit.withDescriptionAndDate(client, data.description(), data.status(), dateStart, dateEnd);
        var newHabit = this.createHabit.create(habit);
        ResponseHabitDto responseDto = new ResponseHabitDto(newHabit);
        URI location = URI.create("/api/habits/" + newHabit.getId());
        log.info("Fim da criação do hábito::HabitController");
        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping("change-habit-status/{id}")
    public ResponseEntity<ResponseHabitDto> changeDo(@PathVariable String id, @RequestBody ChangeDoneDto dateProgress) throws HabitExeption, WeekException {
        log.info("Início da mudança do hábito do status::HabitController");
        Habit habit = this.changeDoHabit.changeDoHabit(id, dateProgress.status());
        log.info("Fim da mudança do hábito do status::HabitController");
        return ResponseEntity.ok(new ResponseHabitDto(habit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseHabitDto> update(@PathVariable String id, @RequestBody UpdateHabitDto data) throws HabitExeption {
        log.info("Início da atualização do hábito::HabitController");
        Habit habit = this.factoryHabit.updateNoDateStater(data.description(), data.done());
        Habit update = this.updateHabit.update(id, habit);
        ResponseHabitDto responseDto = new ResponseHabitDto(update);
        log.info("Fim da atualização do hábito::HabitController");
        return ResponseEntity.ok(responseDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) throws HabitExeption {
        log.info("Início da remoção do hábito::HabitController");
        this.deleteHabit.delete(id);
        log.info("Fim da remoção do hábito::HabitController");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHabitDto> findById(@PathVariable String id) throws HabitExeption {
        log.info("Início da busca do hábito por id::HabitController");
        Habit habit = this.findHabit.findById(id);
        ResponseHabitDto responseHabitDto = new ResponseHabitDto(habit);
        log.info("Fim da busca do hábito por id::HabitController");
        return ResponseEntity.ok(responseHabitDto);
    }

    @GetMapping("find-all/{userId}")
    public ResponseEntity<List<ResponseHabitDto>> findAllByUserId(@PathVariable String userId) throws HabitExeption {
        log.info("Início da busca de todos os hábit::HabitController");
        List<Habit> habits = this.findAllByUser.findAllByUser(userId);
        List<ResponseHabitDto> responseDtos = habits.stream()
                .map(ResponseHabitDto::new)
                .toList();
        log.info("Fim da busca de todos os hábit::HabitController");
        return ResponseEntity.ok(responseDtos);
    }
}
