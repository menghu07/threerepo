package com.apeny.servletjsp.chapter01.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.apeny.servletjsp.common.HtmlBuffer;
import com.apeny.servletjsp.domain.Product;
import com.apeny.servletjsp.domain.ShoppingItem;

@WebServlet(name = "shoppingcartservlet", urlPatterns = {"/session8/products", "/session8/viewProductDetails", "/session8/addToCart", "/session8/viewCart"})
public class ShoppingCartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5067222342077035948L;

	private final String CART_ATTRIBUTE = "cart";
	
	/**
	 * 产品列表
	 */
	private final List<Product> products = new ArrayList<>();
	
	/**
	 * 货币格式化
	 */
	private NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
	
	@Override
	public void init() throws ServletException {
		System.out.println("session shopping cart servlet init........");
		products.add(new Product(201, "Bravo 32' HDTV", "Low-cost HDTV from renowned TV manufacturer", 189.89F));
		products.add(new Product(301, "Bravo BluRay Player", "High quality stylish BluRay player", 89.34F));
		products.add(new Product(401, "Bravo Stereo System", "5 speaker hifi system with iPod player", 128.83F));
		products.add(new Product(501, "Bravo iPod player", "An iPod plug-in that can play mutiple formats", 38.57F));
	}
	
	/**
	 * 分发GET请求
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (uri.endsWith("/products")) {
			sendProductList(req, resp);
		} else if (uri.endsWith("/viewProductDetails")) {
			sendProductDetails(req, resp);
		} else if (uri.endsWith("/viewCart")) {
			showCart(req, resp);
		}
	}

	/**
	 * add to Cart
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// add to Cart
		Integer id = 201;
		int quantity = 1;
		try {
			id = Integer.valueOf(req.getParameter("id"));
			quantity = Integer.valueOf(req.getParameter("quantity"));
		} catch (NumberFormatException ex) {
			//do nothing
			ex.printStackTrace();
		}
		Product product = getProduct(id);
		if (quantity > 0) {
			ShoppingItem item = new ShoppingItem(product, quantity);
			HttpSession session = req.getSession();
			@SuppressWarnings("unchecked")
			List<ShoppingItem> cart = (List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);
			if (cart == null) {
				cart = new ArrayList<>();
			}
			if (cart.contains(item)) {
				//如果已经有产品记录，就把数量添加到当前的数量中
				ShoppingItem existedItem = cart.get(cart.indexOf(item));
				existedItem.setQuantity(existedItem.getQuantity() + quantity);
			} else {
				cart.add(item);
			}
			//必须要重新赋值，到Session中对于redis缓存
			session.setAttribute(CART_ATTRIBUTE, cart);
			sendProductList(req, resp);
		}
	}

	/**
	 * 显示产品列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void sendProductList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HtmlBuffer buffer = new HtmlBuffer("Products");
		buffer.appendBody("<h2>Products</h2>");
		buffer.appendBody("<ul>");
		for (Product p : products) {
			buffer.appendBody("<li>" + p.getName() + "&nbsp;&nbsp;&nbsp;&nbsp; price:" + currencyFormat.format(p.getPrice()) + "&nbsp;&nbsp;&nbsp;&nbsp;<a href='viewProductDetails?id=" + p.getId() + "'>Details</a></li>");
		}
		buffer.appendBody("</ul>");
		buffer.appendBody("<a href='viewCart'>View Cart</a>");
		PrintWriter pw = resp.getWriter();
		pw.println(buffer);
	}
	
	private void showCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		@SuppressWarnings("unchecked")
		List<ShoppingItem> cart = (List<ShoppingItem>) req.getSession().getAttribute(CART_ATTRIBUTE);  
		if (cart == null) {
			return;
		}
		HtmlBuffer html = new HtmlBuffer("Show Cart");
		html.appendStyle("tr {text-align:center;}");
		html.appendBody("<h2>Show Cart</h2>");
		html.appendBody("<table width='600px'>");
		html.appendBody("<tr><th>Product Name</th><th>Price</th><th>Quantity</th><th>Amount</th>");
		BigDecimal sum = new BigDecimal(0);
		for (ShoppingItem item : cart) {
			Product p = item.getProduct();
			Float price = p.getPrice();
			Integer quantity = item.getQuantity();
			BigDecimal subAmount = computeSubAmount(price, quantity);
			sum = sum.add(subAmount);
			html.appendBody("<tr><td><a href='viewProductDetails?id=" + p.getId() + "'>" + p.getName() + "</a></td><td>" + currencyFormat.format(price) 
				+ "</td><td>" + quantity + "个</td><td style='text-align:right;'>小计：" + currencyFormat.format(subAmount.doubleValue()) + "</td>");
		}
		html.appendBody("<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td style='text-align:right;'>总计：" + currencyFormat.format(sum.doubleValue()) + "</td></tr>");
		html.appendBody("<tr><td colspan='4'><a href='products'>Product List</a></td></tr>");
		html.appendBody("</table>");
		html.output(resp);
	}
	
	private void sendProductDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = new Integer(1);
		try {
			id = Integer.valueOf(req.getParameter("id"));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			// do nothing
		}
		Product product = getProduct(id);
		HtmlBuffer html = new HtmlBuffer("Product Details");
		html.appendBody("<h2>Product Details</h2>");
		//默认提交方式GET
		html.appendBody("<form action='addToCart' method='post'>");
		html.appendBody("<input name='id' type='hidden' value='" + id + "'/>");
		html.appendBody("<table>");
		html.appendBody("<tr><td>Name</td><td>" + product.getName() + "</td></tr>");
		html.appendBody("<tr><td>Description</td><td>" + product.getDescription() + "</td></tr>");
		html.appendBody("<tr><td>Price</td><td>" + currencyFormat.format(product.getPrice()) + "</td></tr>");		
		html.appendBody("<tr><td>Quantity</td><td><input name='quantity' value='1' autofocus='autofocus'></td></tr>");
		html.appendBody("<tr><td colspan='2' style='text-align:center'><input type='submit' value='Buy'></td></tr>");
		html.appendBody("<tr><td colspan='2' style='text-align:center'><a href='products'>Product List</a></td></tr>");
		html.appendBody("</table>");
		html.output(resp);
	}
	
	private Product getProduct(Integer id) throws ServletException {
		for (Product p : products) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		throw new ServletException("not found product, id: " + id);
	}
	
	/**
	 * 单价*数量=小计
	 * @param price
	 * @param quantity
	 * @return
	 */
	private BigDecimal computeSubAmount(Float price, Integer quantity) {
		BigDecimal priceNumeric = new BigDecimal(price);
		BigDecimal quantityNumeric = new BigDecimal(quantity);
		return priceNumeric.multiply(quantityNumeric);
	}
}
