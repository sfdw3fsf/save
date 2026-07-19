import { useEffect, useRef, useState } from "react";
import { twMerge } from "tailwind-merge";
import ArrowLeftIcon from "../icon/ArrowLeftIcon";
import ArrowRightIcon from "../icon/ArrowRightIcon";

function Carousel({ slideImages }: { slideImages: string[] }) {
  const [currentSlide, setCurrentSlide] = useState(0);
  const [imageWidth, setImageWidth] = useState<number | null>(null);
  const imageRef = useRef<HTMLImageElement | null>(null);

  const [containerWidth, setContainerWidth] = useState<number | null>(null);
  const containerRef = useRef<HTMLImageElement | null>(null);

  const nextAction = () => {
    setCurrentSlide((curr) => (curr === slideImages.length - 1 ? 0 : curr + 1));
  };

  const preAction = () => {
    setCurrentSlide((curr) => (curr === 0 ? slideImages.length - 1 : curr - 1));
  };

  useEffect(() => {
    const autoNext = setInterval(nextAction, 3500);
    return () => clearInterval(autoNext);
  }, []);

  // Get image width after component mounts
  useEffect(() => {
    if (imageRef.current) {
      setImageWidth(imageRef.current.offsetWidth);
    }
  }, [window.innerWidth, imageRef?.current?.offsetWidth]);

  useEffect(() => {
    if (containerRef.current) {
      setContainerWidth(containerRef.current.offsetWidth);
    }
  }, [window.innerWidth]);

  console.log("imageWidth", imageWidth);
  console.log("containerWidth", containerWidth);
  console.log("body", window.innerWidth);

  return (
    <div className='relative max-w-[1296px] overflow-hidden' ref={containerRef}>
      <div
        className='flex gap-10 transition-transform duration-500 ease-out'
        style={{
          transform: `translateX(-${currentSlide * (((imageWidth! + 40) * 100) / containerWidth!)}%)`,
        }}>
        {slideImages?.map((image, index) => (
          <img
            key={index}
            ref={imageRef}
            // Set the ref only for the current slide
            src={image}
            className={twMerge(
              "h-[405px] w-[720px] rounded opacity-50 transition-opacity duration-1000 ease-in-out max-md:h-[180px]",
              currentSlide === index ? "opacity-100" : null,
            )}
          />
        ))}
      </div>
      <div className='absolute left-4 top-4 flex gap-3'>
        <button onClick={preAction} className='flex size-8 items-center justify-center rounded-full bg-white'>
          <ArrowLeftIcon />
        </button>
        <button onClick={nextAction} className='flex size-8 items-center justify-center rounded-full bg-white'>
          <ArrowRightIcon />
        </button>
      </div>
    </div>
  );
}

export default Carousel;
