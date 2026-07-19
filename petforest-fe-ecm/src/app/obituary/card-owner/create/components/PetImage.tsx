type PetImageProps = {
  url: string;
};
const PetImage = ({ url }: PetImageProps) => {
  return <img className='rounded-[20px] aspect-square' alt={`obituary-pet-${url}`} src={url} loading='lazy' />;
};

export default PetImage;
