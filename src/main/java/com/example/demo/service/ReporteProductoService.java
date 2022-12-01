package com.example.demo.service;

import com.example.demo.common.JasperReportManager;
import com.example.demo.enums.TipoReporteEnum;
import com.example.demo.model.ReporteProductosDTO;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Service
public class ReporteProductoService {

    @Autowired
    private JasperReportManager reportManager;

    @Autowired
    private DataSource dataSource;

    public ReporteProductosDTO obtenerReporteProductos(Map<String, Object> params)
        throws JRException, IOException, SQLException{
        String fileName = "reporte_proveedor";
        ReporteProductosDTO dto = new ReporteProductosDTO();
        String extension = params.get("tipo").toString().equalsIgnoreCase(TipoReporteEnum.EXCEL.name()) ? ".xlsx" : ".pdf";
        dto.setFileName(fileName+extension);

        ByteArrayOutputStream stream = reportManager.export(fileName, params.get("tipo").toString(), params,
                dataSource.getConnection());

        byte[] bs = stream.toByteArray();
        dto.setStream(new ByteArrayInputStream(bs));
        dto.setLenght(bs.length);

        return dto;
    }


}
