package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {
	private Filme rogueOne;
	private Sala sala3D;
	private Sessao SessaoDasDez;
	private Sessao SessaoDasTreze;
	private Sessao SessaoDasDezoito;
	
	@Before
	public void preparaSessoes(){
		this.rogueOne = new Filme("Rogue One", Duration.ofMinutes(120),"SCI-FI", BigDecimal.ONE);
		this.sala3D = new Sala("Sala 3D", BigDecimal.TEN);
		
		this.SessaoDasDez = new Sessao(LocalTime.parse("10:00:00"),rogueOne, sala3D);
		this.SessaoDasTreze = new Sessao(LocalTime.parse("13:00:00"),rogueOne, sala3D);
		this.SessaoDasDezoito = new Sessao(LocalTime.parse("18:00:00"),rogueOne, sala3D);
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario(){
		List<Sessao> sessoes = Arrays.asList(SessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(SessaoDasDez));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente(){
		
		List<Sessao> sessoes = Arrays.asList(SessaoDasDez);
		Sessao sessao = new Sessao(SessaoDasDez.getHorario().minusHours(1),rogueOne, sala3D);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente(){
		
		List<Sessao> sessoes = Arrays.asList(SessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessao = new Sessao(SessaoDasDez.getHorario().plusHours(1),rogueOne,sala3D);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}
	
	@Test
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes(){
	List<Sessao> sessoes = Arrays.asList(SessaoDasDez, SessaoDasDezoito);
	GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
	Assert.assertTrue(gerenciador.cabe(SessaoDasTreze));
	}
}
