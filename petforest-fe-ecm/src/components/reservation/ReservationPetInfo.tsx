import { getReservationPetTypeApi, reservationPetTypeKeys } from '@/store/reservation/reservation.api';
import { reservationPetTypeRespToOption } from '@/store/reservation/reservation.service';
import { exceptForNumberSymbols } from '@/utils/constant/reservation/reservation-info.constant';
import { CheckIcon, ChevronDownIcon } from '@chakra-ui/icons';
import { Button, Divider, Menu, MenuButton, MenuItem, MenuList } from '@chakra-ui/react';
import { keepPreviousData, useQuery } from '@tanstack/react-query';
import { isEmpty } from 'lodash';
import { useMemo, useState } from 'react';
import { Controller, useFormContext } from 'react-hook-form';

export const ReservationPetInfo = () => {
  const { register, control } = useFormContext();

  const [showMessage, setShowMessage] = useState(false);

  const validateWeight = (value: string): boolean => {
    return !isEmpty(value) && !isNaN(Number(value)) && Number(value) > 0;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setShowMessage(validateWeight(value));
  };

  const { data: reservationPetTypeData } = useQuery({
    queryKey: reservationPetTypeKeys.lists(),
    queryFn: () => getReservationPetTypeApi(),
    select: (response) => {
      return response.data.data;
    },
    placeholderData: keepPreviousData,
  });

  const petTypeOptions = useMemo(() => {
    if (!reservationPetTypeData) return [];
    return reservationPetTypeRespToOption(reservationPetTypeData);
  }, [reservationPetTypeData]);

  return (
    <div className='my-[30px] text-left'>
      <div className='font-bold text-[17px] mb-[14px] md:mb-[18px]'>반려동물 정보</div>
      <input
        {...register('petInfo.petName')}
        type='text'
        id='pet_name'
        className='input-form mb-[13px]'
        placeholder='아이 이름을 입력해주세요.'></input>
      <div className='relative flex gap-2 w-full'>
        <input
          {...register('petInfo.petWeight')}
          type='number'
          id='pet_weight'
          onChange={handleChange}
          className='input-form border-0'
          step='any'
          onKeyDown={(e) => exceptForNumberSymbols.includes(e.key) && e.preventDefault()}
          onWheel={() => {
            const activeElement = document.activeElement as HTMLInputElement;
            activeElement.blur();
          }}
          placeholder='체중을 입력 해주세요'></input>
        <span className='text-esset'>kg</span>
      </div>
      {showMessage && <p className='text-[15px]'>*10kg 기준이며, 초과될 경우 비용이 추가될 수 있습니다.</p>}
      <Controller
        name='petInfo.petClassification'
        control={control}
        render={({ field: { value = null, onChange } }) => (
          <Menu>
            <MenuButton
              className={`input-form !bg-transparent mt-[13px] text-left text-grey-text text-[16px] font-medium ${value !== null && '!text-black'}`}
              as={Button}
              _active={{
                borderColor: '#0d4221 !important',
                borderWidth: '1px !important',
              }}
              rightIcon={<ChevronDownIcon boxSize={5} className=' text-grey-charcoal' />}>
              {petTypeOptions.find((option) => option.value === value)?.label || '반려 동물 구분'}
            </MenuButton>

            <MenuList className='!py-0 min-w-[237px] shadow-[2px_2px_8px_0_rgba(0,0,0,0.16)] rounded-[12px]'>
              {petTypeOptions.map((option, index) => (
                <>
                  <MenuItem minH='48px' onClick={() => onChange(option.value)} lineHeight='48px' color='#000' className='option'>
                    <div className={`${option.value !== value ? '!hidden' : 'selected'}`}>
                      <CheckIcon boxSize={3} mr={3} />
                    </div>
                    {option.label}
                  </MenuItem>
                  {index < petTypeOptions.length - 1 && <Divider />}
                </>
              ))}
            </MenuList>
          </Menu>
        )}
      />
    </div>
  );
};
