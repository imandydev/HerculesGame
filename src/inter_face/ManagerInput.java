package inter_face;

import java.io.IOException;

public interface ManagerInput {
	void loadImage() throws IOException;

	void loadAnimation() throws IOException;

	int[][] getPhysicalMap() throws IOException;
}
