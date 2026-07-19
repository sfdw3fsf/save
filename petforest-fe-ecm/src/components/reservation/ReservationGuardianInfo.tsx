import { ReservationDTO } from '@/store/reservation/dto/reservation.dto';
import { OPTION_GUARDIAN_NUMBER_VISITOR } from '@/utils/constant/reservation/menu-pet-form.constant';
import { CheckIcon, ChevronDownIcon } from '@chakra-ui/icons';
import { Button, Divider, Menu, MenuButton, MenuItem, MenuList } from '@chakra-ui/react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';

export const ReservationGuardianInfo = () => {
  const { register, control, setValue } = useFormContext<ReservationDTO>();
  const message = useWatch({
    control,
    name: 'guardianInfo.message',
  });

  const handlePhoneNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    let value = e.target.value.replace(/-/g, '');
    if (value.length > 4) {
      value = `${value.slice(0, 4)}-${value.slice(4)}`;
    }
    setValue('guardianInfo.phoneNumber', value);
  };

  return (
    <div className='my-[30px] text-left'>
      <div className='font-bold text-[17px] mb-[14px] md:mb-[18px]'>보호자 정보</div>
      <input
        {...register('guardianInfo.guardianName')}
        type='text'
        id='guardian_name'
        className='input-form  mb-[13px]'
        placeholder='이름을 입력해주세요'></input>
      <Controller
        name='guardianInfo.numOfVisitor'
        control={control}
        render={({ field: { value = null, onChange } }) => (
          <Menu>
            <MenuButton
              className={`input-form !bg-transparent text-left text-grey-text text-[16px] font-medium ${value !== null && '!text-black'}`}
              as={Button}
              _active={{ borderColor: '#0d4221 !important', borderWidth: '1px !important' }}
              rightIcon={<ChevronDownIcon boxSize={5} className='text-grey-charcoal' />}>
              {OPTION_GUARDIAN_NUMBER_VISITOR.find((option) => option.value === value)?.label || '방문 인원을 선택해주세요'}
            </MenuButton>
            <MenuList>
              {OPTION_GUARDIAN_NUMBER_VISITOR.map((option, index) => (
                <>
                  <MenuItem
                    key={option.value}
                    minH='48px'
                    onClick={() => onChange(option.value)}
                    lineHeight='48px'
                    color='#000'
                    className='option'>
                    <div className={`${option.value !== value ? '!hidden' : 'selected'}`}>
                      <CheckIcon boxSize={3} mr={3} />
                    </div>
                    {option.label}
                  </MenuItem>
                  {index < OPTION_GUARDIAN_NUMBER_VISITOR.length - 1 && <Divider />}
                </>
              ))}
            </MenuList>
          </Menu>
        )}
      />
      <div className='flex flex-wrap mt-4 text-center' id='check_reserve'>
        <div className='relative flex w-full spec-input'>
          <input
            type='text'
            className='short-input !text-black input !p-0 !border-r-0'
            value='010'
            placeholder='010'
            readOnly={true}
          />
          <input
            {...register('guardianInfo.phoneNumber')}
            type='text'
            id='phone_number'
            maxLength={9}
            placeholder='-없이 휴대폰 번호를 입력 해주세요.'
            className='input !border-l-0'
            onChange={handlePhoneNumberChange}
          />
        </div>
      </div>
      {/* !py-[15px] px-[28px] input-form */}
      <div className='relative mt-2.5 w-full !h-[96px] border rounded-lg overflow-hidden '>
        <textarea
          {...register('guardianInfo.message')}
          placeholder='전달하고 싶으신 메시지를 입력해주세요'
          maxLength={1000}
          className='w-full h-full text-[16px] focus:outline-none focus:ring-0 focus:border-green-main focus:border-[1px] focus:rounded-lg focus:shadow-none resize-none !py-[15px] px-[28px]'
        />
        <span className='absolute text-grey-text right-3 bottom-3'>{message.length}/1000</span>
      </div>
    </div>
  );
};
