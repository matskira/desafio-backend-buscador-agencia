package br.com.santander.buscadora_agencia.application.usecase;

import br.com.santander.buscadora_agencia.domain.model.Agencia;
import br.com.santander.buscadora_agencia.domain.service.CalculadoraDistanciaService;
import br.com.santander.buscadora_agencia.ports.input.BuscarAgenciasPorDistanciaUseCase;
import br.com.santander.buscadora_agencia.ports.output.AgenciaRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementação do caso de uso de buscar agências por distância
 */
@Service
public class BuscarAgenciasPorDistanciaUseCaseImpl implements BuscarAgenciasPorDistanciaUseCase {

    private final AgenciaRepository agenciaRepository;
    private final CalculadoraDistanciaService calculadoraDistanciaService;

    public BuscarAgenciasPorDistanciaUseCaseImpl(
            AgenciaRepository agenciaRepository,
            CalculadoraDistanciaService calculadoraDistanciaService) {
        this.agenciaRepository = agenciaRepository;
        this.calculadoraDistanciaService = calculadoraDistanciaService;
    }

    @Override
    public Map<String, String> buscarPorDistancia(Integer posX, Integer posY) {
        // Validação de entrada
        if (posX == null) {
            throw new IllegalArgumentException("Coordenada X não pode ser nula");
        }
        if (posY == null) {
            throw new IllegalArgumentException("Coordenada Y não pode ser nula");
        }

        List<Agencia> agencias = agenciaRepository.buscarTodas();

        // Calcula distância de cada agência e cria pares (agência, distância)
        Map<Agencia, Double> agenciasComDistancia = agencias.stream()
                .collect(Collectors.toMap(
                        agencia -> agencia,
                        agencia -> calculadoraDistanciaService.calcularDistancia(
                                posX, posY, agencia.getPosX(), agencia.getPosY()
                        )
                ));

        // Ordena por distância (mais próxima primeiro) e cria o mapa de resposta
        return agenciasComDistancia.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getNome(),
                        entry -> "distancia = " + calculadoraDistanciaService.formatarDistancia(entry.getValue()),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
