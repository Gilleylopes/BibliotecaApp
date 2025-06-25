package br.com.tjrj.biblioteca.service.relatorios;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private static final Logger log = LoggerFactory.getLogger(RelatorioService.class);

    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;

    public byte[] gerarRelatorioLivros() {
        try (
            Connection conexao = dataSource.getConnection();
            InputStream template = resourceLoader
                    .getResource("classpath:relatorios/livros_por_autor.jrxml")
                    .getInputStream()
        ) {
            JasperReport relatorio = JasperCompileManager.compileReport(template);
            JasperPrint preenchido = JasperFillManager.fillReport(relatorio, new HashMap<>(), conexao);
            return JasperExportManager.exportReportToPdf(preenchido);

        } catch (Exception e) {
            log.error("Erro na geração do relatório", e); 
            throw new RuntimeException("Erro ao gerar o relatório de livros por autor", e);
        }
    }
}
