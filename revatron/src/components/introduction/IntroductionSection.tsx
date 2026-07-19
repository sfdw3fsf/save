type Props = {
  tittle_one: string;
  content_one: string;
  image_one: string;
  tittle_two: string;
  content_two: string;
  image_two: string;
};

function IntroductionSection({ tittle_one, image_one, content_one, tittle_two, content_two, image_two }: Props) {
  return (
    <section className='w-full max-w-[1296px] px-4 py-12'>
      {/* Part1 */}
      <div className='flex flex-wrap items-start justify-center gap-16 max-md:gap-6 xl:justify-start'>
        {/* Title */}
        <div className='flex max-w-[536px] flex-col gap-6'>
          <h1 className='text-3xl font-bold text-warm-950'>{tittle_one}</h1>
          <p className='text-lg font-normal text-stone-600'>{content_one}</p>
        </div>
        <div
          style={{
            WebkitMaskImage: "radial-gradient(circle, black 50%, rgba(0, 0, 0, 0.5) 50%)",
            maskImage: "radial-gradient(circle, black 50%, rgba(0, 0, 0, 0.5) 50%)",
            // ["WebkitMaskMode"]: "alpha",
            maskMode: "alpha",
          }}
          className='relative h-[512px] w-full max-w-[520px] overflow-hidden opacity-40'>
          <img
            src='/public/images/introduction/introduction.svg'
            alt='image_one'
            className='absolute inset-0 h-auto w-full object-cover'
          />
          <img
            src='/public/images/introduction/emulated_1.svg'
            alt='image_one'
            className='absolute left-48 top-28 w-full max-w-[357px] opacity-40'
          />
        </div>
      </div>
      {/* Part 2 */}
      <div className='flex flex-wrap-reverse items-end justify-center gap-16 max-md:gap-6 xl:justify-end'>
        {/* Image */}
        <div className='w-full max-w-[520px]'>
          <img src={image_two} alt='image_one' />
        </div>
        {/* Title */}
        <div className='flex max-w-[536px] flex-col gap-6'>
          <h1 className='text-3xl font-bold text-warm-950'>{tittle_two}</h1>
          <p className='text-lg font-normal text-stone-600'>
            {content_two.split("\n").map((item, index) => (
              <>
                <span key={index}>{item}</span>
                <br /> <br />
              </>
            ))}
          </p>
        </div>
      </div>
    </section>
  );
}

export default IntroductionSection;
