/*
 * Copyright 2010, Andrew M Gibson
 *
 * www.andygibson.net
 *
 * This file is part of DataValve.
 *
 * DataValve is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DataValve is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with DataValve.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.fluttercode.datavalve.samples.swingdemo;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import org.hibernate.Session;
import org.fluttercode.datavalve.client.swing.ProviderTableModel;
import org.fluttercode.datavalve.provider.hibernate.HibernateDataProvider;
import org.fluttercode.datavalve.samples.swingdemo.model.Person;

/**
 * @author Andy Gibson
 * 
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private JScrollPane pane;

	public static void main(String[] args) {
		Main main = new Main();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setSize(300, 300);
		main.setVisible(true);
		main.setTitle("IndexedDataProviderCache demo");
	}

	public Main() {
		initForm();
	}

	protected void initForm() {

		DataInitializer di = new DataInitializer();
		di.init();
		table = new JTable();
		pane = new JScrollPane(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		getContentPane().add(pane);

		final ProviderTableModel<Person> model = initModel(di.getSession());

		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Phone");
		table.setModel(model);

		initClickableColumns(model);

	}

	private void initClickableColumns(final ProviderTableModel<Person> model) {
		// add mouse listener to the header that sets the paginator order key
		// for the paginator used in the model
		table.getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TableColumnModel cm = table.getColumnModel();

				int ci = cm.getColumnIndexAtX(e.getX());
				int idx = table.convertColumnIndexToModel(ci);
				switch (idx) {
				case 0:
					model.getPaginator().changeOrderKey("id");
					break;
				case 1:
					model.getPaginator().changeOrderKey("name");
					break;
				case 2:
					model.getPaginator().changeOrderKey("phone");
					break;
				}
				model.invalidate();
				table.repaint();
			}
		});

	}

	private ProviderTableModel<Person> initModel(Session session) {
		HibernateDataProvider<Person> provider = new HibernateDataProvider<Person>();
		provider.setSession(session);
		provider.getOrderKeyMap().put("id", "p.id");
		provider.getOrderKeyMap().put("name", "p.lastName,p.firstName");
		provider.getOrderKeyMap().put("phone", "p.phone");

		provider.init(Person.class, "p");

		return new ProviderTableModel<Person>(provider) {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected Object getColumnValue(Person object, int column) {
				switch (column) {
				case 0:
					return object.getId();
				case 1:
					return object.getName();
				case 2:
					return object.getPhone();
				default:
					throw new RuntimeException(
							"Unexpected column for person object " + column);
				}
			}

		};
	}

}
