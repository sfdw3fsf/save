/* eslint-disable @next/next/no-img-element */

const HomeAutoSlider = () => {
  return (
    <div className='w-full overflow-hidden py-[5px] md:py-[10px] border-t border-t-[#ccc]'>
      <div className='marquee-container'>
        <div className='marquee-wrapper'>
          {[...Array(14)].map((_, index) => (
            <img key={index} className='marquee-item' src={`/images/home/home-logo-${index + 1}.png`} alt='' />
          ))}
        </div>
        <div className='marquee-wrapper'>
          {[...Array(14)].map((_, index) => (
            <img key={index} className='marquee-item' src={`/images/home/home-logo-${index + 1}.png`} alt='' />
          ))}
        </div>
      </div>
    </div>
  );
};

export default HomeAutoSlider;
