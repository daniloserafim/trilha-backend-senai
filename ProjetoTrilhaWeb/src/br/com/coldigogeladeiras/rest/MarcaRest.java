package br.com.coldigogeladeiras.rest;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.coldigogeladeiras.bd.Conexao;
import br.com.coldigogeladeiras.jdbc.JDBCMarcaDAO;
import br.com.coldigogeladeiras.jdbc.JDBCProdutoDAO;
import br.com.coldigogeladeiras.modelo.Marca;
import br.com.coldigogeladeiras.modelo.Produto;

@Path("marca")
public class MarcaRest extends UtilRest {
	@GET
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar() {
		try {
			List<Marca> listaMarcas = new ArrayList<Marca>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			listaMarcas = jdbcMarca.buscar();
			conec.fecharConexao();
			
			return this.buildResponse(listaMarcas);
			
		} catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscarCoisarada")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarCoisarada(@QueryParam("valorBusca") String nome) {
		try {
			List<Marca> listaMarcas = new ArrayList<Marca>();
			
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			listaMarcas = jdbcMarca.buscarPorNome(nome);
			conec.fecharConexao();
			
			String json = new Gson().toJson(listaMarcas);
			return this.buildResponse(json);
			
		} catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@POST
	@Path("/inserir")
	@Consumes("application/*")
	public Response inserir(String marcaParam) {
		try {
			Marca marca = new Gson().fromJson(marcaParam, Marca.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			List<Marca> marcaIgual = jdbcMarca.buscarPorNome(marca.getNome());
			
			if (!marcaIgual.isEmpty()) {
				return this.buildResponse("Erro. Essa marca j� est� cadastrada!");
			}
			
			boolean retorno = jdbcMarca.inserir(marca);
			String msg = "";
			
			if(retorno) {
				msg = "Marca cadastrada com sucesso!";
			} else {
				msg = "Erro ao cadastrar marca.";
			}
			
			conec.fecharConexao();
			
			return this.buildResponse(msg);
			
		} catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@DELETE
	@Path("/excluir/{id}")
	@Consumes("application/*")
	public Response excluir(@PathParam("id") int id) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			String msg = jdbcMarca.verificarParaExclusao(id);
			
			if (msg == null) {
				
				if (jdbcMarca.deletar(id)) {
					
					msg = "Marca exclu�da com sucesso!";
				} else {
					
					msg = "Erro ao excluir marca!";
				}
			}
		
			conec.fecharConexao();
			
			return this.buildResponse(msg);
			
		} catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@GET
	@Path("/buscaPorId")
	@Consumes("application/*")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscaPorId(@QueryParam("id") int id) {
		
		try {
			Marca marca = new Marca();
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			marca = jdbcMarca.buscaPorId(id);
			
			conec.fecharConexao();
			
			return this.buildResponse(marca);
			
		} catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterar")
	@Consumes("application/*")
	public Response alterar(String marcaParam) {
		try {
			Marca marca = new Gson().fromJson(marcaParam, Marca.class);
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			boolean retorno = jdbcMarca.alterar(marca);
			
			String msg = "";
			if (retorno) {
				msg = "Marca alterado com sucesso!";
			} else {
				msg = "Erro ao alterar marca.";
			}
			
			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
	
	@PUT
	@Path("/alterarStatus")
	@Consumes("application/*")
	public Response alterarStatus(String marcaId) {
		try {
			Conexao conec = new Conexao();
			Connection conexao = conec.abrirConexao();
			JDBCMarcaDAO jdbcMarca = new JDBCMarcaDAO(conexao);
			
			boolean retorno = jdbcMarca.alterarStatus(marcaId);
			
			String msg = "";
			if (retorno) {
				msg = "Status da marca alterado com sucesso!";
			} else {
				msg = "Erro ao alterar status da marca.";
			}
			
			conec.fecharConexao();
			return this.buildResponse(msg);
		} catch(Exception e) {
			e.printStackTrace();
			return this.buildErrorResponse(e.getMessage());
		}
	}
}
