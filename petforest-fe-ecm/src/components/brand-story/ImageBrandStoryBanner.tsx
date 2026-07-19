'use client';
import { useEffect } from 'react';
import Rellax from 'rellax';

interface RellaxFigureProps {
  src: string;
  alt: string;
  rellaxSpeed: string;
  rellaxXsSpeed: string;
  rellaxPercentage?: string;
  id: string;
}

const RellaxFigure = ({ src, alt, rellaxSpeed, rellaxXsSpeed, rellaxPercentage = '0', id }: RellaxFigureProps) => {
  useEffect(() => {
    const element = document.getElementById(id);
    if (element) {
      const rellax = new Rellax(`#${id}`, {
        speed: parseFloat(rellaxSpeed),
        breakpoints: [576, 768, 1200],
      });

      return () => rellax.destroy();
    }
  }, [id, rellaxSpeed]);

  return (
    <figure
      className='rellax'
      data-rellax-speed={rellaxSpeed}
      data-rellax-xs-speed={rellaxXsSpeed}
      data-rellax-percentage={rellaxPercentage}
      id={id}>
      <img src={src} alt={alt} />
    </figure>
  );
};

export default RellaxFigure;
