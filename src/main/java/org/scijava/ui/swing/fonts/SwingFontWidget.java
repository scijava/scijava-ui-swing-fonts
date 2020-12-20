/*-
 * #%L
 * SciJava UI font components for Java Swing
 * %%
 * Copyright (C) 2020 SciJava developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.scijava.ui.swing.fonts;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.drjekyll.fontchooser.FontChooser;
import org.scijava.plugin.Plugin;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

@Plugin(type = InputWidget.class)
public class SwingFontWidget extends SwingInputWidget<Font> implements FontWidget<JPanel>, ChangeListener {

	private FontChooser fontChooser;

	// -- InputWidget methods --

	@Override
	public Font getValue() {
		return fontChooser.getSelectedFont();
	}

	// -- WrapperPlugin methods --

	@Override
	public void set(final WidgetModel model) {
		super.set(model);

		final Font font = (Font) model.getValue();
		fontChooser = font == null ? new FontChooser() : new FontChooser(font);
		setToolTip(fontChooser);
		fontChooser.addChangeListener(this);
		getComponent().add(fontChooser);

		refreshWidget();
	}

	// -- Typed methods --

	@Override
	public boolean supports(final WidgetModel model) {
		return super.supports(model) && model.isType(Font.class);
	}

	// -- AbstractUIInputWidget methods --

	@Override
	protected void doRefresh() {
		Font font = (Font) get().getValue();
		if (fontChooser.getSelectedFont().equals(font)) return;
		fontChooser.setSelectedFont(font);
	}

	// -- ChangeListener methods --

	@Override
	public void stateChanged(ChangeEvent e) {
		updateModel();
	}

}
