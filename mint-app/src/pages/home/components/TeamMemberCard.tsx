import i18next from 'i18next';
import React, { useState } from 'react';

type TeamMemberCardProps = {
  imgSrc: string;
  name: string;
  department: string;
  hospital: string;
  experience: string;
};

const TeamMemberCard = ({ imgSrc, name, department, hospital, experience }: TeamMemberCardProps) => {
  const [isHovered, setIsHovered] = useState(false);
  const isEnglish = i18next.language === 'en';

  return (
    <div
      className='w-64 h-[276px] rounded-xl shadow-md bg-white transition-all duration-300 hover:bg-primary-600'
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}>
      <div className='h-full relative pt-6 px-3'>
        <div className='flex flex-col items-center'>
          <img
            src={imgSrc}
            className={`transition-all duration-300 object-fill mx-auto
              ${isHovered ? 'w-36 h-36 rounded-none border-none' : 'w-24 h-24 rounded-full border'}`}
            alt={`Avatar of ${name}`}
          />

          <div
            className={`transition-all duration-300 flex flex-col gap-1 mt-6
              ${
                isHovered
                  ? 'absolute left-2 right-2 bottom-3 backdrop-blur-sm bg-white/30 rounded-xl items-start p-3'
                  : 'relative items-center'
              }`}>
            <p className={`text-lg font-semibold transition-colors ${isHovered ? 'text-white' : ''}`}>{name}</p>
            <p className={`text-base transition-colors ${isHovered ? 'text-white' : 'text-primary-600'}`}>{department}</p>
            {isEnglish ? (
              <>
                <p className={`text-base transition-colors ${isHovered ? 'text-white' : 'text-[#475467]'}`}>
                  {hospital} Clinic, {experience}
                </p>
              </>
            ) : (
              <>
                <p className={`text-base transition-colors ${isHovered ? 'text-white' : 'text-[#475467]'}`}>{hospital}</p>
                <p className={`text-base transition-colors ${isHovered ? 'text-white' : 'text-[#2c3036]'}`}>{experience}</p>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default TeamMemberCard;
