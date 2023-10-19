package br.com.apiattornatus.interceptor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.java.Log;

@Component
@Log
public class LoggerInterceptor implements HandlerInterceptor {

	private String pattern = "dd/MM/yyyy HH:mm:ss";
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());

	private static final String IDENTIFICADOR = "identificador";
	private static final String URL = "url";
	private static final String METODO = "metodo";
	private static final String STATUS = "status";
	private static final String INICIO = "inicio";
	private static final String FIM = "fim";
	private static final String DURACAO = "duracao";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Instant inicio = Instant.now();
		String identificador = UUID.randomUUID().toString();
		request.setAttribute(IDENTIFICADOR, identificador);
		request.setAttribute(INICIO, inicio.toEpochMilli());

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {

		Instant fim = Instant.now();
		Instant inicio = Instant.ofEpochMilli((Long) request.getAttribute(INICIO));

		Map<String, Object> dados = new LinkedHashMap<>();
		dados.put(IDENTIFICADOR, (String) request.getAttribute(IDENTIFICADOR));
		dados.put(URL, request.getRequestURL().toString());
		dados.put(METODO, request.getMethod());
		dados.put(STATUS, response.getStatus());
		dados.put(INICIO, formatter.format(inicio));
		dados.put(FIM, formatter.format(fim));
		dados.put(DURACAO, fim.toEpochMilli() - inicio.toEpochMilli() + "ms");
		log.info(dados.toString());
	}
}
