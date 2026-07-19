import useDaumnAddress from '@/hook/useDaumnAddress';
import { ReservationDTO } from '@/store/reservation/dto/reservation.dto';
import { OPTION_FUNERAL_SERVICE } from '@/utils/constant/reservation/menu-pet-form.constant';
import { CheckIcon, ChevronDownIcon, SearchIcon } from '@chakra-ui/icons';
import {
  Accordion,
  AccordionButton,
  AccordionItem,
  AccordionPanel,
  Button,
  Divider,
  Menu,
  MenuButton,
  MenuItem,
  MenuList,
} from '@chakra-ui/react';
import { useEffect, useMemo, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';

export const ReservationServiceInfo = () => {
  const { register, control, getValues, setValue } = useFormContext<ReservationDTO>();
  const { openPostcode } = useDaumnAddress();

  const [indexExpand, setIndexExpand] = useState<number[]>([]);

  const formValues = useWatch({
    control,
  });

  const isNotSellectedAddition = useMemo(() => {
    return getValues('additionalInfo.isNotSellected') === null ? false : getValues('additionalInfo.isNotSellected');
  }, [formValues]);

  const handleSellectAdditional = (tabNumber: number, isExpanded: boolean) => {
    if (!isExpanded) {
      if (tabNumber === 0) {
        setIndexExpand([0]);
      } else {
        setValue('additionalInfo.isNotSellected', true);
        const updatedIndexExpand = [...indexExpand, tabNumber].filter((index) => index !== 0);
        setIndexExpand(updatedIndexExpand);
      }
    } else {
      if (tabNumber === 0) {
        setIndexExpand([]);
      } else {
        setValue('additionalInfo.isNotSellected', true);
        const updatedIndexExpand = indexExpand.filter((number) => number !== tabNumber);
        setIndexExpand(updatedIndexExpand);
      }
    }
  };

  const onOpenPostCode = async () => {
    const data = await openPostcode();
    const address = data.address || '';
    const province = data.sido;
    const district = data.sigungu;
    setValue('additionalInfo.funeralService.address', `${address}, ${province}, ${district}`);
  };

  useEffect(() => {
    setValue('additionalInfo.isNotSellected', indexExpand.includes(0));
    setValue('additionalInfo.funeralService.isActive', indexExpand.includes(1));
    setValue('additionalInfo.flowerBouquetService', indexExpand.includes(2));
    setValue('additionalInfo.lucetteService', indexExpand.includes(3));
  }, [indexExpand, setValue]);

  return (
    <div className='my-[30px] text-left'>
      <div className='font-bold text-[17px] mb-[14px] md:mb-[18px]'>부가 서비스</div>
      <Accordion allowMultiple index={indexExpand}>
        <Controller
          name='additionalInfo.isNotSellected'
          control={control}
          render={({ field: { value, onChange } }) => (
            <AccordionItem className='rounded-[5px] border border-lightest-grey mb-[13px]'>
              {({ isExpanded }) => (
                <>
                  <h2>
                    <AccordionButton
                      className='h-[54px]'
                      _hover={{ bg: 'none' }}
                      onClick={() => {
                        handleSellectAdditional(0, isExpanded);
                        onChange(isExpanded);
                      }}>
                      <div
                        className={`rounded-[6px] mr-[14px] h-[26px] w-[26px] flex justify-center items-center border border-lightest-grey ${isExpanded && 'bg-green-main'}`}>
                        {isExpanded && <CheckIcon color='white' />}
                      </div>
                      <div className={`text-[16px] text-grey-text ${isExpanded && '!text-dark-grey'}`}>선택안함</div>
                    </AccordionButton>
                  </h2>
                </>
              )}
            </AccordionItem>
          )}
        />

        <AccordionItem
          className={`border rounded-[5px] border-lightest-grey mb-[13px] ${isNotSellectedAddition ? 'bg-grey-silver' : ''}`}>
          {({ isExpanded }) => (
            <>
              <h2>
                <AccordionButton
                  className={`${!isExpanded ? 'h-[54px] !border-b-0' : '!pt-[16px] h-[43px]'}`}
                  _hover={{ bg: 'none' }}
                  onClick={() => {
                    handleSellectAdditional(1, isExpanded);
                  }}>
                  <div
                    className={`rounded-[6px] mr-[14px] h-[26px] w-[26px] flex justify-center items-center border border-lightest-grey  ${isExpanded && 'bg-green-main'}`}>
                    {isExpanded && <CheckIcon color='white'></CheckIcon>}
                  </div>
                  <div className={`text-[16px] text-grey-text ${isExpanded && '!text-dark-grey'}`}>운구 서비스</div>
                </AccordionButton>
                <AccordionPanel className='!pt-0'>
                  <div>
                    <div className='text-[13px] font-normal pb-[19px] md:pb-[21px] '>
                      *편도로 운영되며 비용은 지역에 따라 3~8만원 추가 비용이 발생합니다.
                    </div>
                    <Controller
                      name='additionalInfo.funeralService.accompanny'
                      control={control}
                      render={({ field: { value = null, onChange } }) => {
                        return (
                          <Menu>
                            <MenuButton
                              className={`input-form !bg-transparent text-left text-grey-text !text-[13px] font-medium ${value !== null && '!text-black'} md:!text-[15px]`}
                              as={Button}
                              _active={{
                                borderColor: '#0d4221 !important',
                                borderWidth: '1px !important',
                              }}
                              rightIcon={<ChevronDownIcon boxSize={5} className='text-grey-charcoal' />}>
                              {OPTION_FUNERAL_SERVICE.find((option) => option.value === value)?.label || '동행 여부'}
                            </MenuButton>
                            <MenuList>
                              {OPTION_FUNERAL_SERVICE.map((option, index) => (
                                <>
                                  <MenuItem
                                    key={option.value}
                                    minH='48px'
                                    onClick={() => {
                                      onChange(option.value);
                                    }}
                                    lineHeight='48px'
                                    color='#000'
                                    className='option'>
                                    <div className={`${option.value !== value ? '!hidden' : 'selected'}`}>
                                      <CheckIcon boxSize={3} mr={3} />
                                    </div>

                                    {option.label}
                                  </MenuItem>
                                  {index < OPTION_FUNERAL_SERVICE.length - 1 && <Divider />}
                                </>
                              ))}
                            </MenuList>
                          </Menu>
                        );
                      }}
                    />
                    <div className='relative flex gap-2 w-full mt-[10px] md:mt-[12px] '>
                      <input
                        {...register('additionalInfo.funeralService.address')}
                        type='text'
                        onClick={onOpenPostCode}
                        readOnly
                        className='input-form border-0 !text-[13px] md:!text-[15px]'
                        placeholder='주소 검색'></input>
                      <SearchIcon className='text-esset text-grey-charcoal' />
                    </div>
                    <input
                      {...register('additionalInfo.funeralService.detailedAddress')}
                      type='text'
                      className='input-form border-0 mt-[10px] md:mt-[12px] !text-[13px] md:!text-[15px]'
                      placeholder='상세 주소를 입력해주세요'></input>
                    <SearchIcon className='text-esset text-grey-charcoal' />
                  </div>
                </AccordionPanel>
              </h2>
            </>
          )}
        </AccordionItem>
        <AccordionItem
          className={`border rounded-[5px] border-lightest-grey mb-[13px] ${isNotSellectedAddition ? 'bg-grey-silver' : ''}`}>
          {({ isExpanded }) => (
            <>
              <h2>
                <AccordionButton
                  className={`h-[54px] ${!isExpanded && ' !border-b-0'}`}
                  _hover={{ bg: 'none' }}
                  onClick={() => {
                    handleSellectAdditional(2, isExpanded);
                  }}>
                  <div
                    className={`rounded-[6px] mr-[14px] h-[26px] w-[26px] flex justify-center items-center border border-lightest-grey  ${isExpanded && 'bg-green-main'}`}>
                    {isExpanded && <CheckIcon color='white'></CheckIcon>}
                  </div>
                  <div className={`text-[16px] text-grey-text ${isExpanded && '!text-dark-grey'}`}>꽃다발 서비스</div>
                </AccordionButton>
                <AccordionPanel className='!py-0 '>
                  <div className='text-[13px] pb-[19px] md:pb-[21px] font-normal'>
                    *추모 절차 시 꽃장식을 하고 싶은 보호자님을 위한 서비스로 제공되는 꽃은 계절에 따라 변동이 있을 수 있습니다.
                  </div>
                </AccordionPanel>
              </h2>
            </>
          )}
        </AccordionItem>
        <AccordionItem
          className={`border rounded-[5px] border-lightest-grey mb-[13px] ${isNotSellectedAddition ? 'bg-grey-silver' : ''}`}>
          {({ isExpanded }) => (
            <>
              <h2>
                <AccordionButton
                  className={`h-[54px] ${!isExpanded && ' !border-b-0'}`}
                  _hover={{ bg: 'none' }}
                  onClick={() => {
                    handleSellectAdditional(3, isExpanded);
                  }}>
                  <div
                    className={`rounded-[6px] mr-[14px] h-[26px] w-[26px] flex justify-center items-center border border-lightest-grey  ${isExpanded && 'bg-green-main'}`}>
                    {isExpanded && <CheckIcon color='white'></CheckIcon>}
                  </div>
                  <div className={`text-[16px] text-grey-text ${isExpanded && '!text-dark-grey'}`}>루세떼 (추모보석) 장례</div>
                </AccordionButton>
                <AccordionPanel className='!py-0'>
                  <div className=' pb-[19px] md:pb-[21px] font-normal'>
                    <div className='text-[13px]'>
                      루세떼 장례는 반려동물 유골분으로 공정하는 추모보석입니다. 공정시간은 장례(화장)이후 별도로 4시간 정도
                      소요되며 당일 제작을 원하시는 경우 오후 3시 이전 시간으로 예약을 해주시기 바랍니다.
                    </div>
                    <div className='text-[12px] mt-[15px] text-grey-text'>
                      · 당일 루세떼 공정이 어려울 시 추후 다른 날짜에 공정이 가능합니다.
                    </div>
                  </div>
                </AccordionPanel>
              </h2>
            </>
          )}
        </AccordionItem>
      </Accordion>
    </div>
  );
};
