package br.com.monitodehabitos.monitodehabitos.infra.controller.clientDto;

public record ResetPassordDTO(
        String email,
        String code
) {
}
