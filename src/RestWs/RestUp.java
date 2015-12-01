package RestWs;

import huffman.HuffmanCompression;
import huffman.Decompress;
import huffman.Node;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.management.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.Session;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import Hibernate.HibernateUtil;
import Model.DbTree;

@RequestScoped
@Path("")
public class RestUp {
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public void getFile(@QueryParam("name") String name) throws IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();

		org.hibernate.Query query = session
				.createQuery("FROM File where md5hash = :md5 ");
		query.setParameter("md5", name);

		List<Model.File> results = ((org.hibernate.Query) query).list();

		Decompress dc = new Decompress("S");
		for (DbTree md : results.get(0).getStockDailyRecords()) {
			
			dc.getFrequencyHashMap().put(md.getSymbol().shortValue(),
					md.getFrequency());

		}
		dc.setDecompressedsize(String.valueOf(results.get(0).getSize()));
		dc.setLastbit(results.get(0).getOutbit());
		dc.decompressFile("/home/katakonst/" + name + ".huff",
				"/home/katakonst/boss2.txt");
		

	}

	@GET
	@Path("/hib")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFie(@QueryParam("name") String name) {

		File file = new File("/home/katakonst/test.jar");
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=" + name);
		return response.build();

	}

	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) {

		String fileName = "";

		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");

		for (InputPart inputPart : inputParts) {

			try {

				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);

				InputStream inputStream = inputPart.getBody(InputStream.class,
						null);

				byte a[] = new byte[2000];
				int ct;
				ByteArrayOutputStream bs = new ByteArrayOutputStream();
				while ((ct = inputStream.read(a)) > 0) {
					bs.write(a, 0, ct);
				}

				String md5 = DigestUtils.md5Hex(bs.toByteArray());
				String flnm = md5 + "." + FilenameUtils.getExtension(fileName);
				fileName = "/home/katakonst/" + md5 + "."
						+ FilenameUtils.getExtension(fileName);

				writeFile(bs.toByteArray(), fileName);

				HuffmanCompression com = new HuffmanCompression();

				com.compress(fileName, "/home/katakonst/" + md5 + ".huff");
				Session session = HibernateUtil.getSessionFactory()
						.openSession();

				session.beginTransaction();
				Model.File user = new Model.File();

				user.setFileName(flnm);
				user.setMd5hash(md5);
				user.setSize(bs.size());
				user.setOutbit(com.getOutbit());

				Set<DbTree> itemsSet = new HashSet<DbTree>();
				Iterator frequencyIterator = com.getFrequencyHashMap()
						.entrySet().iterator();
				while (frequencyIterator.hasNext()) {
					Map.Entry pair = (Map.Entry) frequencyIterator.next();
					DbTree item3 = new DbTree();
					item3.setFrequency((Integer) pair.getValue());
					item3.setSymbol(new Integer((Short) pair.getKey()));
					item3.setFile(user);
					itemsSet.add(item3);

				}
				user.setStockDailyRecords(itemsSet);
				session.save(user);

				for (DbTree s : itemsSet) {
					session.save(s);
				}
				session.getTransaction().commit();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return Response.status(200).entity("fisier: " + fileName).build();

	}

	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition")
				.split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	private void writeFile(byte[] content, String filename) throws IOException {

		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fop = new FileOutputStream(file);

		fop.write(content);
		fop.flush();
		fop.close();

	}
}
