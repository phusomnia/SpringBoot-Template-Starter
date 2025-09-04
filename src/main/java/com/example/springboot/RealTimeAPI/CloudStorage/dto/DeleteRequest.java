package com.example.springboot.RealTimeAPI.CloudStorage.dto;

public class DeleteRequest {
    public CloudProvider cloudProvider;
    public String public_id;
    
    public DeleteRequest(
        CloudProvider cloudProvider,
        String public_id
    )
    {
        this.cloudProvider = cloudProvider;
        this.public_id = public_id;
    }
}
