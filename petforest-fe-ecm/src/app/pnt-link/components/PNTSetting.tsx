type PNTSettingProps = {
  onClick?: () => void;
  displayName?: string;
  index: number;
  petName?: string;
  guardianName?: string;
  selectedImage?: string;
};

const PNTSetting = ({ onClick, displayName = '', petName, guardianName, selectedImage = '', index }: PNTSettingProps) => {
  const background = selectedImage !== '' ? `url(${selectedImage})` : 'white';
  return (
    <div
      onClick={onClick}
      className={`relative border border-neutral-40 h-[118px] rounded-[10px] overflow-hidden ${onClick ? 'cursor-pointer' : ''} `}
      style={{
        background: background,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
      }}>
      <div className='relative h-full'>
        <div className='absolute inset-0 bg-black opacity-60 z-0'></div>
        <div className='absolute inset-0 z-10 flex flex-col justify-center items-center'>
          <div className='!text-white'>{displayName}</div>
          <div className='!text-white'>{petName}</div>
          <div className='!text-white'>{guardianName}</div>
        </div>
      </div>
    </div>
  );
};

export default PNTSetting;
