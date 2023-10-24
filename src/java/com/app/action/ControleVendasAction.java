/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.action;

import com.app.dao.ControleVendasDAO;
import com.app.dao.PessoaFisicaDAO;
import com.app.dao.VeiculoDAO;
import com.app.model.ControleVendasModel;
import com.app.model.PessoaFisicaModel;
import com.app.model.VeiculoModel;
import com.app.util.Errors;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author macuser
 */
public class ControleVendasAction extends IDRAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, Errors errors, String action) {
        String forward = action;
        if (action.equals("page")) {
            this.page(form, request, errors);
        } else if (action.equals("pesquisarVeiculosVenda")) {
            this.pesquisarVeiculosVenda(form, request, errors);
        } else if (action.equals("carregarMarcaVeiculo") || action.equals("carregarMarcaVeiculoP")) {
            this.carregarMarcaVeiculo(form, request, errors);
        } else if (action.equals("carregarVeiculos") || action.equals("carregarVeiculosP")) {
            this.carregarVeiculos(form, request, errors);
        } else if (action.equals("validarVeiculo")) {
            this.validarVeiculo(form, request, errors);
        } else if (action.equals("save")) {
            this.save(form, request, errors);
        } else if (action.equals("pesquisarVeiculos")) {
            this.pesquisarVeiculos(form, request, errors);
        } else if (action.equals("detalhesVeiculo")) {
            this.detalhesVeiculo(form, request, errors);
        } else if (action.equals("pageAtualizar")) {
            this.detalhesVeiculo(form, request, errors);
        } else if (action.equals("atualizar")) {
            this.atualizar(form, request, errors);
        } else if (action.equals("excluir")) {
            this.excluir(form, request, errors);
        } else if (action.equals("pageParcela")) {
            this.pageParcela(form, request, errors);
        } else if (action.equals("atualizarPacela")) {
            this.atualizarPacela(form, request, errors);
        }

        return mapping.findForward(forward);
    }

    private void page(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = new ControleVendasModel();
        HttpSession session = request.getSession();
        Connection conn = null;

        try {
            conn = connectionPool.getConnection();

            session.removeAttribute("listaTipoVeiculo");
            session.removeAttribute("listaMarcaVeiculo");
            session.removeAttribute("listaVeiculoPorMarca");
            session.removeAttribute("ControleVendasModel");

            //obter lista dos tipos de veiculos
            List<VeiculoModel> listaTipoVeiculo = VeiculoDAO.getInstance().obterListaTipoVeiculos(conn);
            session.setAttribute("listaTipoVeiculo", listaTipoVeiculo);

            controleVendasModel.setIdTipoVeiculo(0);
            session.setAttribute("ControleVendasModel", controleVendasModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }
    
    private void pesquisarVeiculosVenda(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = new ControleVendasModel();
        HttpSession session = request.getSession();
        Connection conn = null;

        try {
            conn = connectionPool.getConnection();

            session.removeAttribute("listaTipoVeiculo");
            session.removeAttribute("listaMarcaVeiculo");
            session.removeAttribute("listaVeiculoPorMarca");
            session.removeAttribute("ControleVendasModel");
            
            //carregar lista de pessoas que ja compraram veiculos
            List<ControleVendasModel> listaPessoasVendaVeiculo = ControleVendasDAO.getInstance().obterPessoasComVendaVeiculo(conn);
            session.setAttribute("listaPessoasVendaVeiculo", listaPessoasVendaVeiculo);

            //obter lista dos tipos de veiculos
            List<VeiculoModel> listaTipoVeiculo = VeiculoDAO.getInstance().obterListaTipoVeiculos(conn);
            session.setAttribute("listaTipoVeiculo", listaTipoVeiculo);

            controleVendasModel.setIdTipoVeiculo(0);
            session.setAttribute("ControleVendasModel", controleVendasModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void carregarMarcaVeiculo(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = (ControleVendasModel) form;
        HttpSession session = request.getSession();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            session.removeAttribute("listaVeiculoPorMarca");

            //obter lista das marcas de veiculos ja cadastradas
            List<VeiculoModel> listaMarcaVeiculo = VeiculoDAO.getInstance().obterListaMarcaVeiculoPorTipo(conn, controleVendasModel.getIdTipoVeiculo());
            session.setAttribute("listaMarcaVeiculo", listaMarcaVeiculo);

            session.setAttribute("ControleVendasModel", controleVendasModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void carregarVeiculos(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = (ControleVendasModel) form;
        HttpSession session = request.getSession();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //obter lista dos veiculos cadastrado por marca
            List<VeiculoModel> listaVeiculoPorMarca = VeiculoDAO.getInstance().obterListaVeiculoPorMarca(conn, controleVendasModel.getIdMarcaVeiculo());
            session.setAttribute("listaVeiculoPorMarca", listaVeiculoPorMarca);

            session.setAttribute("ControleVendasModel", controleVendasModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void validarVeiculo(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = (ControleVendasModel) form;
        Connection conn = null;
        HttpSession session = request.getSession();
        try {
            conn = connectionPool.getConnection();

            //obter lista de pessoas cadastradas
            PessoaFisicaModel pessoaFisicaModel = new PessoaFisicaModel();
            List<PessoaFisicaModel> listaPessoaFisica = PessoaFisicaDAO.getInstance().searchAll(conn, pessoaFisicaModel);
            session.setAttribute("listaPessoaFisica", listaPessoaFisica);

            request.setAttribute("detalhesVeiculo", "true");
            session.setAttribute("ControleVendasModel", controleVendasModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void save(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = (ControleVendasModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            String folderImg = ControleVendasModel.folderImg;

            //fazer upload da imagem caso tenha no servidor na pasta imagens_veiculos e salvar no banco
            boolean isMultipart = FileUpload.isMultipartContent(request);
            String path = getServlet().getServletContext().getRealPath("/") + folderImg;
            String nomeArquivo = null;

            if (isMultipart) {
                FormFile formFile = controleVendasModel.getFileImg1();
                nomeArquivo = controleVendasModel.getChassi() + ".jpg";

                //colocar o caminho da pasta e do nome do arquivo
                String pathImg = folderImg + "/" + nomeArquivo;
                controleVendasModel.setPathImg1(pathImg);

                File folder = new File(path);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                File newFile = new File(path, nomeArquivo);
                if (!newFile.exists()) {
                    FileOutputStream fos = new FileOutputStream(newFile);
                    fos.write(formFile.getFileData());
                    fos.flush();
                    fos.close();
                }
            }

            int vlEntrada = Integer.parseInt(controleVendasModel.getValorEntrada().replace(",", "").replace(".", ""));
            int vlCobrado = Integer.parseInt(controleVendasModel.getPrecoVenda().replace(",", "").replace(".", ""));
            int vlRestante = vlCobrado - vlEntrada;
            int vlParcelas = vlRestante / controleVendasModel.getQtdParcelas();

            boolean isOK = false;

            if (controleVendasModel.getDiaPagamentoPrestacao() == 0) {
                //pagamento a vista. Nesse caso, temos que verificar se o vaor de Entrada eh igual ao valor cobrado
                if (vlCobrado <= vlEntrada) {
                    isOK = true;
                } else {
                    isOK = false;
                    errors.error("The Entry value cannot be less than the Charged value!!");
                }
            } else {
                //pagamento parcelado, nem que seja 1 parcela
                isOK = true;
            }

            if (isOK) {
                int idVendaVeiculo = ControleVendasDAO.getInstance().save(conn, controleVendasModel, vlEntrada, vlCobrado, vlRestante);

                //verificar se eu preciso salvar na tabela controle_venda_veiculo
                if (controleVendasModel.getQtdParcelas() > 1) {
                    LocalDate dataAtual = LocalDate.now(ZoneId.systemDefault());
                    for (int i = 1; i <= controleVendasModel.getQtdParcelas(); i++) {
                        dataAtual = dataAtual.plusMonths(1);
                        if (controleVendasModel.getDiaPagamentoPrestacao() == 30 && dataAtual.getMonth().getValue() == 2) {
                            dataAtual = dataAtual.withDayOfMonth(28);
                        } else {
                            dataAtual = dataAtual.withDayOfMonth(controleVendasModel.getDiaPagamentoPrestacao());
                        }

                        String dataFormatada = String.valueOf(dataAtual);
                        //salvar na tabela controle_venda_veiculo
                        ControleVendasDAO.getInstance().salvarControleVendaVeiculo(conn, idVendaVeiculo, controleVendasModel.getDiaPagamentoPrestacao(), dataFormatada, vlParcelas);

                    }

                }

            }

            errors.error("Salvo com Sucesso!!");
            controleVendasModel.setIdVeiculo(0);
            this.page(form, request, errors);
            request.setAttribute("ControleVendasModel", controleVendasModel);

        } catch (Exception e) {
            e.printStackTrace();
            errors.error("Ocorreu um Erro!!");
        } finally {
            connectionPool.free(conn);
        }

    }

    private void pesquisarVeiculos(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = (ControleVendasModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            List<ControleVendasModel> listaVeiculos = ControleVendasDAO.getInstance().pesquisarVendaVeiculos(conn, controleVendasModel);

            request.setAttribute("listaVeiculos", listaVeiculos);
            request.setAttribute("ControleVendasModel", controleVendasModel);
        } catch (Exception e) {
            e.printStackTrace();
            errors.error("Ocorreu um Erro!!");
        } finally {
            connectionPool.free(conn);
        }
    }

    private void detalhesVeiculo(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = (ControleVendasModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            controleVendasModel = ControleVendasDAO.getInstance().detalhesVeiculo(conn, controleVendasModel.getIdVendaVeiculo());

            request.setAttribute("ControleVendasModel", controleVendasModel);
        } catch (Exception e) {
            e.printStackTrace();
            errors.error("Ocorreu um Erro!!");
        } finally {
            connectionPool.free(conn);
        }
    }

    private void atualizar(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = (ControleVendasModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            String folderImg = ControleVendasModel.folderImg;

            //fazer upload da imagem caso tenha no servidor na pasta imagens_veiculos e salvar no banco
            boolean isMultipart = FileUpload.isMultipartContent(request);
            String path = getServlet().getServletContext().getRealPath("/") + folderImg;
            String nomeArquivo = null;

            if (isMultipart) {
                FormFile formFile = controleVendasModel.getFileImg1();
                nomeArquivo = controleVendasModel.getChassi() + ".jpg";

                //colocar o caminho da pasta e do nome do arquivo
                String pathImg = folderImg + "/" + nomeArquivo;
                controleVendasModel.setPathImg1(pathImg);

                File folder = new File(path);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                File newFile = new File(path, nomeArquivo);
                FileOutputStream fos = new FileOutputStream(newFile);
                fos.write(formFile.getFileData());
                fos.flush();
                fos.close();

            }

            ControleVendasDAO.getInstance().atualizar(conn, controleVendasModel);

            errors.error("Updated Successfully!!");
            request.setAttribute("ControleVendasModel", controleVendasModel);
        } catch (Exception e) {
            e.printStackTrace();
            errors.error("An error has occurred!!");
        } finally {
            connectionPool.free(conn);
        }
    }

    private void excluir(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = (ControleVendasModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //verificar se ja existe alguma parcela paga na tabela controle_venda_veiculo
            //se nao tiver, pode ser excluido, mas se ja tiver deve alertar
            boolean isExistePagamento = ControleVendasDAO.getInstance().verificaPagamentoVeiculo(conn, controleVendasModel.getIdVendaVeiculo());

            //verificar se o valor restante eh maior que 0
            boolean isExisteValorRestante = ControleVendasDAO.getInstance().verificaValorRestante(conn, controleVendasModel.getIdVendaVeiculo());

            if (!isExistePagamento && isExisteValorRestante) {
                //se nao existe nenhum pagamento, eu devo pegar todas as parcelas pelo ID do Controle Vendas para poder excluir
                List<ControleVendasModel> listaParcelas = ControleVendasDAO.getInstance().obterListaParcelasPorID(conn, controleVendasModel.getIdVendaVeiculo());
                for (ControleVendasModel parcela : listaParcelas) {
                    //deletar cada parcela pelo id do IDCONTROLE
                    ControleVendasDAO.getInstance().excluirControleVendaVeiculo(conn, parcela.getIdControleVenda());
                }

                ControleVendasDAO.getInstance().excluir(conn, controleVendasModel.getIdVendaVeiculo());
                errors.error("Deleted Successfully!!");
            } else {
                errors.error("It cannot be deleted, as it already has a paid installment.");
            }

            //carregar a lista atualizada
            List<ControleVendasModel> listaVeiculos = ControleVendasDAO.getInstance().pesquisarVendaVeiculos(conn, controleVendasModel);

            request.setAttribute("listaVeiculos", listaVeiculos);
            request.setAttribute("ControleVendasModel", controleVendasModel);
        } catch (Exception e) {
            e.printStackTrace();
            errors.error("An error has occurred!!");
        } finally {
            connectionPool.free(conn);
        }
    }

    private void pageParcela(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendasModel = (ControleVendasModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            //obter dados do veiculo pelo id_controle_venda
            controleVendasModel = ControleVendasDAO.getInstance().obterDadosVeiculosIdControleVendas(conn, controleVendasModel.getIdVendaVeiculo());

            //obter lista das parcelas do veiculo
            List<ControleVendasModel> listaParcelas = ControleVendasDAO.getInstance().obterListaParcelasPorID(conn, controleVendasModel.getIdVendaVeiculo());

            request.setAttribute("listaParcelas", listaParcelas);
            request.setAttribute("ControleVendasModel", controleVendasModel);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

    private void atualizarPacela(ActionForm form, HttpServletRequest request, Errors errors) {
        ControleVendasModel controleVendas = (ControleVendasModel) form;
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();

            int vlParcelaPaga = Integer.parseInt(controleVendas.getValorParcelaPaga().replace(",", "").replace(".", "").replace(" ", ""));
            int vlValorRestante = Integer.parseInt(controleVendas.getValorRestante().replace(",", "").replace(".", ""));
            int valorRestanteAtualizado = vlValorRestante - vlParcelaPaga;

            //verificar se é a ultima parcela, se for eu preciso fazer uma logica para que o valor seja igual o valor restante
            boolean isUltimaParcela = ControleVendasDAO.getInstance().isUltimaParcelaAberta(conn, controleVendas.getIdVendaVeiculo());
            if (isUltimaParcela && valorRestanteAtualizado != 0) {
                //se for a ultima parcela aberta -> o valor pago deve ser igual ao valor restante
                errors.error("The value of the last installment must be equal to the Remaining value. The value must be: " + vlValorRestante);

            } else {
                //atualizar os dados da tabela controle_shaken
                ControleVendasDAO.getInstance().atualizarParcelaVeiculo(conn, controleVendas);

                //atualizar o valor restante na tabela shaken
                ControleVendasDAO.getInstance().atualizarValorRestante(conn, valorRestanteAtualizado, controleVendas.getIdVendaVeiculo());

            }

            //obter dados do veiculo atualizar por ID
            controleVendas = ControleVendasDAO.getInstance().obterDadosVeiculosIdControleVendas(conn, controleVendas.getIdVendaVeiculo());

            List<ControleVendasModel> listaParcelas = ControleVendasDAO.getInstance().obterListaParcelasPorID(conn, controleVendas.getIdVendaVeiculo());

            request.setAttribute("listaParcelas", listaParcelas);
            request.setAttribute("ControleVendasModel", controleVendas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionPool.free(conn);
        }
    }

}
