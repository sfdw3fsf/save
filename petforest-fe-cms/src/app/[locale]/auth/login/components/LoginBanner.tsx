export const LoginBanner = () => {
  return (
    <div className='hidden lg:block py-6 pr-6 h-full relative '>
      <img src='/images/LoginImage.jpg' alt='login' className='object-cover h-full rounded-[12px]' />
      <div className='bg-green-shade opacity-80 absolute inset-0 my-6 mr-6 rounded-[12px]'></div>
      <div
        className='absolute inset-0 my-6 mr-6 bg-no-repeat bg-cover rounded-[12px]'
        style={{ backgroundImage: 'url(/images/Lights.svg)' }}></div>
      <div className='absolute font-eastSeaDokdo leading-[53px] text-[63px] right-[64px] bottom-[56px] whitespace-pre-line text-white'>
        {`Everlasting\nMemories`}
      </div>
    </div>
  );
};
