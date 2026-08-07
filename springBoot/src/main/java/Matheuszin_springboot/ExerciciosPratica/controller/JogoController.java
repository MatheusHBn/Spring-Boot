package Matheuszin_springboot.ExerciciosPratica.controller;

import Matheuszin_springboot.ExerciciosPratica.domain.Jogo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1")
public class JogoController {

    @GetMapping("jogos")
    public List<Jogo> list1() {
        return List.of(
                new Jogo("Nine Sols", "RogueLike", 89.5),
                new Jogo("Balatro", "RogueLike", 49.24),
                new Jogo("Assasin's Creed: Black Flag", "Mundo aberto", 241.8),
                new Jogo("Bloons TD 6", "Tower Defense", 29.5),
                new Jogo("Watch Dogs 2", "Mundo aberto", 149));
    }

    @GetMapping("promocoes")
    public List<Jogo> list2() {
        List<Jogo> jogos = List.of(
                new Jogo("Nine Sols", "RogueLike", 89.5),
                new Jogo("Balatro", "RogueLike", 49.24),
                new Jogo("Assasin's Creed: Black Flag", "Mundo aberto", 241.8),
                new Jogo("Bloons TD 6", "Tower Defense", 29.5),
                new Jogo("Watch Dogs 2", "Mundo aberto", 149));
        List<Jogo> list = jogos.stream().filter(jogo -> jogo.preco() < 100).findAny().stream().toList();
        return list;
    }

    @GetMapping("gratis")
    public List<Jogo> list3() {
        List<Jogo> jogos = List.of(
                new Jogo("Nine Sols", "RogueLike", 89.5),
                new Jogo("Balatro", "RogueLike", 49.24),
                new Jogo("Assasin's Creed: Black Flag", "Mundo aberto", 241.8),
                new Jogo("Bloons TD 6", "Tower Defense", 29.5),
                new Jogo("Watch Dogs 2", "Mundo aberto", 149));
        List<Jogo> list = jogos.stream().filter(jogo -> jogo.preco() == 0).findAny().stream().toList();
        return list;
    }
}
