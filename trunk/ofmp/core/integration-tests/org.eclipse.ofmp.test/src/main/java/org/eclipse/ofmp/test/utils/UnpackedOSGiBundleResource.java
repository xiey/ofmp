package org.eclipse.ofmp.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.core.io.UrlResource;

public class UnpackedOSGiBundleResource extends UrlResource {

	public UnpackedOSGiBundleResource(URL aUrl) {
		super(aUrl);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return super.getURL().openStream();
	}
}
