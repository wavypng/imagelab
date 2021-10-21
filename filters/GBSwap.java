package filters;

import imagelab.ImageFilter;
import imagelab.ImgProvider;

/**
 * An imageLab filter that swaps the green and blue values of each pixel.
 */
public class GBSwap implements ImageFilter {

  /**
   * The filtered image.
   */
  private ImgProvider filteredImage;

  /**
   * The filter itself.
   *
   * @param ip the image to be filtered.
   */
  public void filter(final ImgProvider ip) {
    short[][] green = ip.getGreen();
    short[][] blue = ip.getBlue();

    filteredImage = new ImgProvider();
    filteredImage.setColors(blue, ip.getGreen(), green, ip.getAlpha());
    filteredImage.showPix("Blue <=> Green");
  } //filter

  /**
   * Retrieve the filtered image.
   *
   * @return the filtered image.
   */
  public ImgProvider getImgProvider() {
    return filteredImage;
  } //getImgProvider

  /**
   * Retrieve the name of the filter to add to the menu.
   *
   * @return the filter's menu item label
   */
  public String getMenuLabel() {
    return "Red-Blue and Green-Blue Swap";
  } //getMenuLabel

}
